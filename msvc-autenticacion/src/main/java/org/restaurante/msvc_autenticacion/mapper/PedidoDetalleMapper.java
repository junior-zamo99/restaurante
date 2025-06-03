package org.restaurante.msvc_autenticacion.mapper;


import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleDTO;
import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleSimpleDTO;
import org.restaurante.msvc_autenticacion.model.PedidoDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PedidoDetalleMapper {

    @Autowired
    @Lazy
    private PedidoMapper pedidoMapper;

    @Autowired
    private ProductoMapper productoMapper;

    public PedidoDetalleDTO toDto(PedidoDetalle pedidoDetalle) {
        if (pedidoDetalle == null) {
            return null;
        }

        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setPedidoDetalleId(pedidoDetalle.getPedidoDetalleId());
        dto.setCantidad(pedidoDetalle.getCantidad());
        dto.setSubtotal(pedidoDetalle.getSubtotal());
        dto.setEstado(pedidoDetalle.getEstado() != null ?
                pedidoDetalle.getEstado().name() : null);
        dto.setNotas(pedidoDetalle.getNotas());

        if (pedidoDetalle.getPedido() != null) {
            dto.setPedido(pedidoMapper.toSimpleDto(pedidoDetalle.getPedido()));
        }

        if (pedidoDetalle.getProducto() != null) {
            dto.setProducto(productoMapper.toSimpleDto(pedidoDetalle.getProducto()));
        }

        return dto;
    }

    public PedidoDetalleSimpleDTO toSimpleDto(PedidoDetalle pedidoDetalle) {
        if (pedidoDetalle == null) {
            return null;
        }

        return new PedidoDetalleSimpleDTO(
                pedidoDetalle.getPedidoDetalleId(),
                pedidoDetalle.getCantidad(),
                pedidoDetalle.getSubtotal(),
                pedidoDetalle.getProducto() != null ? pedidoDetalle.getProducto().getNombre() : null,
                pedidoDetalle.getEstado() != null ? pedidoDetalle.getEstado().name() : null
        );
    }
}
