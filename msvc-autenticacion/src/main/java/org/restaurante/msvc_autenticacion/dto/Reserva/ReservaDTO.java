package org.restaurante.msvc_autenticacion.dto.Reserva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.Mesa.MesaSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long reservaId;
    private String fechaReserva;
    private String hora;
    private Integer cantidadPersonas;
    private MesaSimpleDTO mesa;
    private ClienteSimpleDTO cliente;
    private String estado;
    private TenantSimpleDTO tenant;
    private String observaciones;
    private String createdAt;
}