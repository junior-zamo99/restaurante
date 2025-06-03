package org.restaurante.msvc_autenticacion.dto.Reserva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaSimpleDTO {
    private Long reservaId;
    private String fechaReserva;
    private String hora;
    private Integer cantidadPersonas;
    private Boolean estado;
}