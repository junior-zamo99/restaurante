package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Reserva.ReservaDTO;
import org.restaurante.msvc_autenticacion.dto.Reserva.ReservaSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ReservaMapper {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private MesaMapper mesaMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservaDTO toDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setReservaId(reserva.getReservaId());
        reservaDTO.setCantidadPersonas(reserva.getCantidadPersonas());
        reservaDTO.setEstado(reserva.getEstado());
        reservaDTO.setObservaciones(reserva.getObservaciones());

        if (reserva.getFechaReserva() != null) {
            reservaDTO.setFechaReserva(reserva.getFechaReserva().format(DATE_FORMATTER));
        }

        if (reserva.getHora() != null) {
            reservaDTO.setHora(reserva.getHora().format(TIME_FORMATTER));
        }

        if (reserva.getMesa() != null) {
            reservaDTO.setMesa(mesaMapper.toSimpleDto(reserva.getMesa()));
        }

        if (reserva.getCliente() != null) {
            reservaDTO.setCliente(clienteMapper.toSimpleDto(reserva.getCliente()));
        }

        if (reserva.getTenant() != null) {
            reservaDTO.setTenant(tenantMapper.toSimpleDto(reserva.getTenant()));
        }

        if (reserva.getCreatedAt() != null) {
            reservaDTO.setCreatedAt(reserva.getCreatedAt().format(DATE_TIME_FORMATTER));
        }

        return reservaDTO;
    }

    public ReservaSimpleDTO toSimpleDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        ReservaSimpleDTO reservaSimpleDTO = new ReservaSimpleDTO();
        reservaSimpleDTO.setReservaId(reserva.getReservaId());
        reservaSimpleDTO.setCantidadPersonas(reserva.getCantidadPersonas());
        reservaSimpleDTO.setEstado(reserva.getEstado());

        if (reserva.getFechaReserva() != null) {
            reservaSimpleDTO.setFechaReserva(reserva.getFechaReserva().format(DATE_FORMATTER));
        }

        if (reserva.getHora() != null) {
            reservaSimpleDTO.setHora(reserva.getHora().format(TIME_FORMATTER));
        }

        return reservaSimpleDTO;
    }

}
