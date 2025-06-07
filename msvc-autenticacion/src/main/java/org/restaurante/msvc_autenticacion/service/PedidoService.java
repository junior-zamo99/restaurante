package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoDTO;
import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoInput;
import org.restaurante.msvc_autenticacion.mapper.PedidoMapper;
import org.restaurante.msvc_autenticacion.model.*;
import org.restaurante.msvc_autenticacion.repository.CuentaMesaRepository;
import org.restaurante.msvc_autenticacion.repository.PedidoRepository;
import org.restaurante.msvc_autenticacion.repository.ProductoRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private CuentaMesaRepository cuentaMesaRepository;


    public PedidoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        return pedidoMapper.toDto(pedido);
    }

    public List<PedidoDTO> findByTenantId(Long tenantId) {
        return pedidoRepository.findByTenantTenantId(tenantId).stream()
                .map(pedidoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> findByEstado(Boolean estado) {
        return pedidoRepository.findByEstado(estado).stream()
                .map(pedidoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO create(PedidoInput input) {
        Pedido pedido = new Pedido();
        return saveOrUpdatePedido(pedido, input);
    }

    public List<PedidoDTO> findByCuentaMesaId(Long cuentaMesaId) {
        return pedidoRepository.findByCuentaMesaCuentaMesaId(cuentaMesaId).stream()
                .map(pedidoMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public PedidoDTO update(Long id, PedidoInput input) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        return saveOrUpdatePedido(pedido, input);
    }

    private PedidoDTO saveOrUpdatePedido(Pedido pedido, PedidoInput input) {
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setNotas(input.getNotas());
        pedido.setEstado(input.getEstado());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        pedido.setTenant(tenant);

        if (input.getCuentaMesaId() != null) {
            CuentaMesa cuentaMesa = cuentaMesaRepository.findById(input.getCuentaMesaId())
                    .orElseThrow(() -> new RuntimeException("Cuenta Mesa no encontrada con id: " + input.getCuentaMesaId()));
            pedido.setCuentaMesa(cuentaMesa);
        }


        if (input.getDetalles() != null && !input.getDetalles().isEmpty()) {
            List<PedidoDetalle> detalles = new ArrayList<>();
            Double total = 0.0;

            for (var detalleInput : input.getDetalles()) {
                Producto producto = productoRepository.findById(detalleInput.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + detalleInput.getProductoId()));

                PedidoDetalle detalle = new PedidoDetalle();
                detalle.setCantidad(detalleInput.getCantidad());
                detalle.setSubtotal(BigDecimal.valueOf(producto.getPrecio() * detalleInput.getCantidad()));
                if (detalleInput.getEstado() != null) {
                    detalle.setEstado(EstadoPedidoDetalle.valueOf(detalleInput.getEstado().toUpperCase()));
                } else {
                    detalle.setEstado(EstadoPedidoDetalle.PENDIENTE);
                }
                detalle.setNotas(detalleInput.getNotas());
                detalle.setPedido(pedido);
                detalle.setProducto(producto);
                detalle.setTenant(tenant);

                detalles.add(detalle);
                total += detalle.getSubtotal().doubleValue();
            }

            pedido.setDetalles(detalles);
            pedido.setTotal(total);
        }

        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(savedPedido);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
