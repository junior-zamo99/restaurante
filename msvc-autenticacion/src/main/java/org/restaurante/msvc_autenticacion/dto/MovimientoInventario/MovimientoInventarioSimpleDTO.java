package org.restaurante.msvc_autenticacion.dto.MovimientoInventario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventarioSimpleDTO {

    private Long movimientoId;
    private Double cantidad;
    private String motivo;

}
