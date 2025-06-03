package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleDTO;
import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleInput;
import org.restaurante.msvc_autenticacion.mapper.PedidoDetalleMapper;
import org.restaurante.msvc_autenticacion.model.EstadoPedidoDetalle;
import org.restaurante.msvc_autenticacion.model.Pedido;
import org.restaurante.msvc_autenticacion.model.PedidoDetalle;
import org.restaurante.msvc_autenticacion.model.Producto;
import org.restaurante.msvc_autenticacion.repository.PedidoDetalleRepository;
import org.restaurante.msvc_autenticacion.repository.PedidoRepository;
import org.restaurante.msvc_autenticacion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoDetalleService {

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoDetalleMapper pedidoDetalleMapper;

    public PedidoDetalleDTO findById(Long id) {
        PedidoDetalle pedidoDetalle = pedidoDetalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PedidoDetalle no encontrado con id: " + id));
        return pedidoDetalleMapper.toDto(pedidoDetalle);
    }

    public List<PedidoDetalleDTO> findByPedidoId(Long pedidoId) {
        return pedidoDetalleRepository.findByPedidoPedidoId(pedidoId).stream()
                .map(pedidoDetalleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> findByProductoId(Long productoId) {
        return pedidoDetalleRepository.findByProductoProductoId(productoId).stream()
                .map(pedidoDetalleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDetalleDTO create(PedidoDetalleInput input) {
        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        return saveOrUpdatePedidoDetalle(pedidoDetalle, input);
    }

    @Transactional
    public PedidoDetalleDTO update(Long id, PedidoDetalleInput input) {
        PedidoDetalle pedidoDetalle = pedidoDetalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PedidoDetalle no encontrado con id: " + id));
        return saveOrUpdatePedidoDetalle(pedidoDetalle, input);
    }

    private PedidoDetalleDTO saveOrUpdatePedidoDetalle(PedidoDetalle pedidoDetalle, PedidoDetalleInput input) {
        pedidoDetalle.setCantidad(input.getCantidad());
        if (input.getEstado() != null) {
            pedidoDetalle.setEstado(EstadoPedidoDetalle.valueOf(input.getEstado().toUpperCase()));
        } else {
            pedidoDetalle.setEstado(EstadoPedidoDetalle.PENDIENTE);
        }
        pedidoDetalle.setNotas(input.getNotas());

        Pedido pedido = pedidoRepository.findById(input.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + input.getPedidoId()));
        pedidoDetalle.setPedido(pedido);

        Producto producto = productoRepository.findById(input.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + input.getProductoId()));
        pedidoDetalle.setProducto(producto);

        BigDecimal subtotal = BigDecimal.valueOf(producto.getPrecio() * input.getCantidad());
        pedidoDetalle.setSubtotal(subtotal);

        PedidoDetalle savedPedidoDetalle = pedidoDetalleRepository.save(pedidoDetalle);
        return pedidoDetalleMapper.toDto(savedPedidoDetalle);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (pedidoDetalleRepository.existsById(id)) {
            pedidoDetalleRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
