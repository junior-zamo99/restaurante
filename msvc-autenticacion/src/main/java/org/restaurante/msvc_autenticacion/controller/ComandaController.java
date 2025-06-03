package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleDTO;
import org.restaurante.msvc_autenticacion.model.EstadoPedidoDetalle;
import org.restaurante.msvc_autenticacion.service.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ComandaController {

    @Autowired
    private ComandaService comandaService;


    @QueryMapping
    public List<PedidoDetalleDTO> pedidoDetallesByEstadoAndTenant(
            @Argument String estado,
            @Argument Long tenantId) {
        EstadoPedidoDetalle estadoEnum = EstadoPedidoDetalle.valueOf(estado.toUpperCase());
        return comandaService.findByEstadoAndTenant(estadoEnum, tenantId);
    }

    @QueryMapping
    public List<PedidoDetalleDTO> comandaActivaPorTenant(@Argument Long tenantId) {
        return comandaService.obtenerComandaActivaPorTenant(tenantId);
    }

    @QueryMapping
    public List<PedidoDetalleDTO> pedidosListosParaEntregaPorTenant(@Argument Long tenantId) {
        return comandaService.obtenerListosParaEntregaPorTenant(tenantId);
    }


    @MutationMapping
    public Boolean enviarPedidoAComanda(
            @Argument Long pedidoId,
            @Argument Long tenantId) {
        return comandaService.enviarPedidoAComanda(pedidoId, tenantId);
    }

    @MutationMapping
    public PedidoDetalleDTO cambiarEstadoPedidoDetalle(
            @Argument Long pedidoDetalleId,
            @Argument String nuevoEstado,
            @Argument Long tenantId) {
        EstadoPedidoDetalle estadoEnum = EstadoPedidoDetalle.valueOf(nuevoEstado.toUpperCase());
        return comandaService.cambiarEstado(pedidoDetalleId, estadoEnum, tenantId);
    }

    @MutationMapping
    public PedidoDetalleDTO marcarDetalleComoListo(
            @Argument Long pedidoDetalleId,
            @Argument Long tenantId) {
        return comandaService.marcarComoListo(pedidoDetalleId, tenantId);
    }

    @MutationMapping
    public PedidoDetalleDTO marcarDetalleComoEntregado(
            @Argument Long pedidoDetalleId,
            @Argument Long tenantId) {
        return comandaService.marcarComoEntregado(pedidoDetalleId, tenantId);
    }

}
