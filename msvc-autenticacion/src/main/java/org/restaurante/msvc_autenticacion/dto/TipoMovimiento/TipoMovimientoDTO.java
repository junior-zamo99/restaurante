package org.restaurante.msvc_autenticacion.dto.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMovimientoDTO {

    private Long tipoMovimientoId;
    private String nombre;
    private String descripcion;
    private boolean afectaInventario;


}
