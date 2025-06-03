package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleDTO;
import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleInput;
import org.restaurante.msvc_autenticacion.service.PedidoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PedidoDetalleController {

    @Autowired
    private PedidoDetalleService pedidoDetalleService;


    @QueryMapping
    public PedidoDetalleDTO pedidoDetalleById(@Argument Long id) {
        return pedidoDetalleService.findById(id);
    }

    @QueryMapping
    public List<PedidoDetalleDTO> pedidoDetallesByPedidoId(@Argument Long pedidoId) {
        return pedidoDetalleService.findByPedidoId(pedidoId);
    }

    @QueryMapping
    public List<PedidoDetalleDTO> pedidoDetallesByProductoId(@Argument Long productoId) {
        return pedidoDetalleService.findByProductoId(productoId);
    }


    @MutationMapping
    public PedidoDetalleDTO createPedidoDetalle(@Argument PedidoDetalleInput input) {
        return pedidoDetalleService.create(input);
    }

    @MutationMapping
    public PedidoDetalleDTO updatePedidoDetalle(@Argument Long id, @Argument PedidoDetalleInput input) {
        return pedidoDetalleService.update(id, input);
    }

    @MutationMapping
    public Boolean deletePedidoDetalle(@Argument Long id) {
        return pedidoDetalleService.delete(id);
    }


}
