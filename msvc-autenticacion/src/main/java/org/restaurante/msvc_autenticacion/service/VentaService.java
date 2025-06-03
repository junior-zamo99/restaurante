package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Venta.VentaDTO;
import org.restaurante.msvc_autenticacion.dto.Venta.VentaInput;
import org.restaurante.msvc_autenticacion.mapper.VentaMapper;
import org.restaurante.msvc_autenticacion.model.CuentaMesa;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.model.Venta;
import org.restaurante.msvc_autenticacion.repository.CuentaMesaRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CuentaMesaRepository cuentaMesaRepository;

    @Autowired
    private VentaMapper ventaMapper;

    public VentaDTO findById(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
        return ventaMapper.toDto(venta);
    }

    public List<VentaDTO> findByTenantId(Long tenantId) {
        return ventaRepository.findByTenantTenantId(tenantId).stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VentaDTO> findByTenantIdAndEstado(Long tenantId, String estado) {
        return ventaRepository.findByTenantTenantIdAndEstado(tenantId, estado).stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VentaDTO> findByCuentaMesaId(Long cuentaMesaId) {
        return ventaRepository.findByCuentaMesaCuentaMesaId(cuentaMesaId).stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VentaDTO> findByTenantIdOrderByFecha(Long tenantId) {
        return ventaRepository.findByTenantIdOrderByFechaVentaDesc(tenantId).stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VentaDTO> findVentasDelDiaByTenantId(Long tenantId) {
        return ventaRepository.findVentasDelDiaByTenantId(tenantId).stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalVentasByTenantIdAndFecha(Long tenantId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        BigDecimal total = ventaRepository.sumTotalByTenantIdAndFechaBetween(tenantId, fechaInicio, fechaFin);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalVentasDelDiaByTenantId(Long tenantId) {
        BigDecimal total = ventaRepository.sumTotalVentasDelDiaByTenantId(tenantId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional
    public VentaDTO create(VentaInput input) {
        if (input.getTenantId() == null) {
            throw new RuntimeException("El tenantId es requerido");
        }

        if (input.getCuentaMesaId() == null) {
            throw new RuntimeException("El cuentaMesaId es requerido");
        }

        if (input.getTotal() == null || input.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El total debe ser mayor a 0");
        }

        Venta venta = new Venta();
        return saveOrUpdateVenta(venta, input);
    }

    @Transactional
    public VentaDTO update(Long id, VentaInput input) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));

        if ("COMPLETADA".equals(venta.getEstado())) {
            throw new RuntimeException("No se puede modificar una venta completada");
        }

        return saveOrUpdateVenta(venta, input);
    }

    private VentaDTO saveOrUpdateVenta(Venta venta, VentaInput input) {
        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        venta.setTenant(tenant);

        CuentaMesa cuentaMesa = cuentaMesaRepository.findById(input.getCuentaMesaId())
                .orElseThrow(() -> new RuntimeException("Cuenta mesa no encontrada con id: " + input.getCuentaMesaId()));
        venta.setCuentaMesa(cuentaMesa);

        venta.setTotal(input.getTotal());
        venta.setObservaciones(input.getObservaciones());

        if (input.getEstado() != null) {
            venta.setEstado(input.getEstado());
        }

        Venta savedVenta = ventaRepository.save(venta);
        return ventaMapper.toDto(savedVenta);
    }

    @Transactional
    public VentaDTO completarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));

        if ("COMPLETADA".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya está completada");
        }

        venta.setEstado("COMPLETADA");
        Venta savedVenta = ventaRepository.save(venta);
        return ventaMapper.toDto(savedVenta);
    }

    @Transactional
    public VentaDTO cancelarVenta(Long id, String motivo) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));

        if ("COMPLETADA".equals(venta.getEstado())) {
            throw new RuntimeException("No se puede cancelar una venta completada");
        }

        venta.setEstado("CANCELADA");
        if (motivo != null && !motivo.isEmpty()) {
            venta.setObservaciones(venta.getObservaciones() != null ?
                    venta.getObservaciones() + " - CANCELADA: " + motivo : "CANCELADA: " + motivo);
        }

        Venta savedVenta = ventaRepository.save(venta);
        return ventaMapper.toDto(savedVenta);
    }

    @Transactional
    public Boolean delete(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));

        if ("COMPLETADA".equals(venta.getEstado())) {
            throw new RuntimeException("No se puede eliminar una venta completada");
        }

        ventaRepository.deleteById(id);
        return true;
    }

}
