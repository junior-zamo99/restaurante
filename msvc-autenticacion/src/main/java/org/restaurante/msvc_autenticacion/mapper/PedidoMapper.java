package org.restaurante.msvc_autenticacion.mapper;


import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoDTO;
import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {
    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    @Lazy
    private PedidoDetalleMapper pedidoDetalleMapper;

    public PedidoDTO toDto(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setPedidoId(pedido.getPedidoId());
        pedidoDTO.setFechaHora(pedido.getFechaHora());
        pedidoDTO.setNotas(pedido.getNotas());
        pedidoDTO.setTotal(pedido.getTotal());
        pedidoDTO.setEstado(pedido.getEstado());

        if (pedido.getTenant() != null) {
            pedidoDTO.setTenant(tenantMapper.toSimpleDto(pedido.getTenant()));
        }

        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            pedidoDTO.setDetalles(pedido.getDetalles().stream()
                    .map(pedidoDetalleMapper::toSimpleDto)
                    .collect(Collectors.toList()));
        }

        return pedidoDTO;
    }

    public PedidoSimpleDTO toSimpleDto(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        return new PedidoSimpleDTO(
                pedido.getPedidoId(),
                pedido.getFechaHora(),
                pedido.getTotal(),
                pedido.getEstado()
        );
    }
}
