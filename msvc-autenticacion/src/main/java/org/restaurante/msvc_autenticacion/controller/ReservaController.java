package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Reserva.ReservaDTO;
import org.restaurante.msvc_autenticacion.dto.Reserva.ReservaInput;
import org.restaurante.msvc_autenticacion.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @QueryMapping
    public ReservaDTO reservaById(@Argument Long id) {
        return reservaService.findById(id);
    }

    @QueryMapping
    public List<ReservaDTO> reservasByTenantId(@Argument Long tenantId) {
        return reservaService.findByTenantId(tenantId);
    }

    @QueryMapping
    public List<ReservaDTO> reservasByTenantIdAndEstado(@Argument Long tenantId, @Argument Boolean estado) {
        return reservaService.findByTenantIdAndEstado(tenantId, estado);
    }

    @QueryMapping
    public List<ReservaDTO> reservasByTenantIdAndClienteId(@Argument Long tenantId, @Argument Long clienteId) {
        return reservaService.findByTenantIdAndClienteId(tenantId, clienteId);
    }

    @QueryMapping
    public List<ReservaDTO> reservasByTenantIdAndMesaId(@Argument Long tenantId, @Argument Long mesaId) {
        return reservaService.findByTenantIdAndMesaId(tenantId, mesaId);
    }

    @QueryMapping
    public List<ReservaDTO> reservasByTenantIdAndFechaReserva(@Argument Long tenantId, @Argument String fecha) {
        return reservaService.findByTenantIdAndFechaReserva(tenantId, fecha);
    }

    @QueryMapping
    public List<ReservaDTO> reservasDelDiaByTenantId(@Argument Long tenantId) {
        return reservaService.findReservasDelDiaByTenantId(tenantId);
    }

    @QueryMapping
    public List<ReservaDTO> reservasActivasDelDiaByTenantId(@Argument Long tenantId) {
        return reservaService.findReservasActivasDelDiaByTenantId(tenantId);
    }

    @MutationMapping
    public ReservaDTO createReserva(@Argument ReservaInput input) {
        return reservaService.create(input);
    }

    @MutationMapping
    public ReservaDTO updateReserva(@Argument Long id, @Argument ReservaInput input) {
        return reservaService.update(id, input);
    }

    @MutationMapping
    public ReservaDTO activarReserva(@Argument Long id) {
        return reservaService.activarReserva(id);
    }

    @MutationMapping
    public ReservaDTO desactivarReserva(@Argument Long id, @Argument String motivo) {
        return reservaService.desactivarReserva(id, motivo);
    }

    @MutationMapping
    public ReservaDTO toggleEstadoReserva(@Argument Long id) {
        return reservaService.toggleEstadoReserva(id);
    }

    @MutationMapping
    public Boolean deleteReserva(@Argument Long id) {
        return reservaService.delete(id);
    }

}
