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

    // Modificado: estado ahora es String
    @QueryMapping
    public List<ReservaDTO> reservasByTenantIdAndEstado(@Argument Long tenantId, @Argument String estado) {
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

    // Nuevo endpoint: Buscar reservas para recordatorio
    @QueryMapping
    public List<ReservaDTO> reservasForReminder(@Argument Long tenantId, @Argument Integer horas) {
        return reservaService.findReservasForReminder(tenantId, horas);
    }

    // Nuevo endpoint: Buscar reserva por teléfono del cliente pendiente de confirmación
    @QueryMapping
    public ReservaDTO reservaPendienteByTelefono(@Argument String telefono) {
        return reservaService.findLatestPendingByClienteTelefono(telefono);
    }

    @MutationMapping
    public ReservaDTO createReserva(@Argument ReservaInput input) {
        return reservaService.create(input);
    }

    @MutationMapping
    public ReservaDTO updateReserva(@Argument Long id, @Argument ReservaInput input) {
        return reservaService.update(id, input);
    }

    // Método actualizado: ahora establece estado = "Activa"
    @MutationMapping
    public ReservaDTO activarReserva(@Argument Long id) {
        return reservaService.activarReserva(id);
    }

    // Método actualizado: ahora establece estado = "Cancelada"
    @MutationMapping
    public ReservaDTO cancelarReserva(@Argument Long id, @Argument String motivo) {
        return reservaService.cancelarReserva(id, motivo);
    }

    // Nuevo endpoint: Marcar como NoShow
    @MutationMapping
    public ReservaDTO marcarNoShow(@Argument Long id) {
        return reservaService.marcarNoShow(id);
    }

    // Método actualizado: ahora alterna entre estados "Activa" y "Cancelada"
    @MutationMapping
    public ReservaDTO toggleEstadoReserva(@Argument Long id) {
        return reservaService.toggleEstadoReserva(id);
    }

    @MutationMapping
    public Boolean deleteReserva(@Argument Long id) {
        return reservaService.delete(id);
    }

    // Nuevos endpoints para gestionar recordatorios y confirmaciones

    @MutationMapping
    public ReservaDTO enviarRecordatorio(@Argument Long id) {
        return reservaService.enviarRecordatorio(id);
    }

    @MutationMapping
    public ReservaDTO confirmarReserva(@Argument Long id) {
        return reservaService.confirmarReserva(id);
    }

    // Endpoint para procesar reservas expiradas sin confirmación
    @MutationMapping
    public List<ReservaDTO> cancelarReservasExpiradas() {
        return reservaService.cancelarReservasExpiradas();
    }

    @QueryMapping
    public List<ReservaDTO> getReservasHoyPendientesDeConfirmacion(@Argument Long tenantId) {
        return reservaService.getReservasHoyPendientesDeConfirmacion(tenantId);
    }
}