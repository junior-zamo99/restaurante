package org.restaurante.msvc_autenticacion.dto.Reserva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaInput {
    private String fechaReserva;
    private String hora;
    private Integer cantidadPersonas;
    private Long mesaId;
    private Long clienteId;
    private Boolean estado;
    private Long tenantId;
    private String observaciones;
}