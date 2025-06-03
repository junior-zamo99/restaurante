package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaDTO;
import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaInput;
import org.restaurante.msvc_autenticacion.mapper.CuentaMesaMapper;
import org.restaurante.msvc_autenticacion.model.Cliente;
import org.restaurante.msvc_autenticacion.model.CuentaMesa;
import org.restaurante.msvc_autenticacion.model.Mesa;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.ClienteRepository;
import org.restaurante.msvc_autenticacion.repository.CuentaMesaRepository;
import org.restaurante.msvc_autenticacion.repository.MesaRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaMesaService {

    @Autowired
    private CuentaMesaRepository cuentaMesaRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaMesaMapper cuentaMesaMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CuentaMesaDTO findById(Long id) {
        CuentaMesa cuentaMesa = cuentaMesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta mesa no encontrada con id: " + id));
        return cuentaMesaMapper.toDto(cuentaMesa);
    }

    public List<CuentaMesaDTO> findByTenantId(Long tenantId) {
        return cuentaMesaRepository.findByTenantTenantId(tenantId).stream()
                .map(cuentaMesaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CuentaMesaDTO> findByTenantIdAndEstado(Long tenantId, String estado) {
        return cuentaMesaRepository.findByTenantTenantIdAndEstado(tenantId, estado).stream()
                .map(cuentaMesaMapper::toDto)
                .collect(Collectors.toList());
    }

    public CuentaMesaDTO findByMesaIdAndEstado(Long mesaId, String estado) {
        CuentaMesa cuentaMesa = cuentaMesaRepository.findByMesaMesaIdAndEstado(mesaId, estado);
        if (cuentaMesa == null) {
            throw new RuntimeException("Cuenta mesa no encontrada para la mesa con id: " + mesaId + " y estado: " + estado);
        }

        return cuentaMesaMapper.toDto(cuentaMesa);
    }

    public List<CuentaMesaDTO> findByTenantIdAndClienteId(Long tenantId, Long clienteId) {
        return cuentaMesaRepository.findByTenantTenantIdAndClienteClienteId(tenantId, clienteId).stream()
                .map(cuentaMesaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CuentaMesaDTO> findCuentasActivasByTenantId(Long tenantId) {
        return cuentaMesaRepository.findCuentasActivasByTenantId(tenantId).stream()
                .map(cuentaMesaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CuentaMesaDTO create(CuentaMesaInput input) {
        if (input.getTenantId() == null) {
            throw new RuntimeException("El tenantId es requerido");
        }

        if (input.getMesaId() == null) {
            throw new RuntimeException("El mesaId es requerido");
        }

        if (input.getNumComensales() == null || input.getNumComensales() <= 0) {
            throw new RuntimeException("El número de comensales debe ser mayor a 0");
        }

        // Verificar que no haya una cuenta activa para esta mesa
        if (cuentaMesaRepository.existsByMesaMesaIdAndEstado(input.getMesaId(), "ACTIVA")) {
            throw new RuntimeException("Ya existe una cuenta activa para esta mesa");
        }

        CuentaMesa cuentaMesa = new CuentaMesa();
        return saveOrUpdateCuentaMesa(cuentaMesa, input);
    }

    @Transactional
    public CuentaMesaDTO update(Long id, CuentaMesaInput input) {
        CuentaMesa cuentaMesa = cuentaMesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta mesa no encontrada con id: " + id));

        return saveOrUpdateCuentaMesa(cuentaMesa, input);
    }

    private CuentaMesaDTO saveOrUpdateCuentaMesa(CuentaMesa cuentaMesa, CuentaMesaInput input) {
        Mesa mesa = mesaRepository.findById(input.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + input.getMesaId()));
        cuentaMesa.setMesa(mesa);

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        cuentaMesa.setTenant(tenant);

        if (input.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(input.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + input.getClienteId()));
            cuentaMesa.setCliente(cliente);
        }

        if (input.getEstado() != null) {
            cuentaMesa.setEstado(input.getEstado());
        }

        cuentaMesa.setNumComensales(input.getNumComensales());

        if (input.getMontoTotal() != null) {
            cuentaMesa.setMontoTotal(input.getMontoTotal());
        } else {
            cuentaMesa.setMontoTotal(BigDecimal.ZERO);
        }

        if (input.getFechaHoraFin() != null && !input.getFechaHoraFin().isEmpty()) {
            try {
                cuentaMesa.setFechaHoraFin(LocalDateTime.parse(input.getFechaHoraFin(), DATE_TIME_FORMATTER));
            } catch (Exception e) {
                throw new RuntimeException("Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm:ss");
            }
        }

        CuentaMesa savedCuentaMesa = cuentaMesaRepository.save(cuentaMesa);
        return cuentaMesaMapper.toDto(savedCuentaMesa);
    }

    @Transactional
    public CuentaMesaDTO cerrarCuentaMesa(Long id) {
        CuentaMesa cuentaMesa = cuentaMesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta mesa no encontrada con id: " + id));

        if (!"ACTIVA".equals(cuentaMesa.getEstado())) {
            throw new RuntimeException("Solo se pueden cerrar cuentas activas");
        }

        cuentaMesa.setEstado("CERRADA");
        cuentaMesa.setFechaHoraFin(LocalDateTime.now());

        CuentaMesa savedCuentaMesa = cuentaMesaRepository.save(cuentaMesa);
        return cuentaMesaMapper.toDto(savedCuentaMesa);
    }

    @Transactional
    public Boolean delete(Long id) {
        CuentaMesa cuentaMesa = cuentaMesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta mesa no encontrada con id: " + id));

        if ("ACTIVA".equals(cuentaMesa.getEstado())) {
            throw new RuntimeException("No se puede eliminar una cuenta activa");
        }

        cuentaMesaRepository.deleteById(id);
        return true;
    }

}
