package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoDTO;
import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoInput;
import org.restaurante.msvc_autenticacion.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @QueryMapping
    public PedidoDTO pedidoById(@Argument Long id) {
        return pedidoService.findById(id);
    }

    @QueryMapping
    public List<PedidoDTO> pedidosByTenantId(@Argument Long tenantId) {
        return pedidoService.findByTenantId(tenantId);
    }

    @QueryMapping
    public List<PedidoDTO> pedidosByEstado(@Argument Boolean estado) {
        return pedidoService.findByEstado(estado);
    }

    @QueryMapping
    public List<PedidoDTO> pedidosByCuentaMesaId(@Argument Long cuentaMesaId) {
        return pedidoService.findByCuentaMesaId(cuentaMesaId);
    }

    @MutationMapping
    public PedidoDTO createPedido(@Argument PedidoInput input) {
        return pedidoService.create(input);
    }

    @MutationMapping
    public PedidoDTO updatePedido(@Argument Long id, @Argument PedidoInput input) {
        return pedidoService.update(id, input);
    }

    @MutationMapping
    public Boolean deletePedido(@Argument Long id) {
        return pedidoService.delete(id);
    }
}
