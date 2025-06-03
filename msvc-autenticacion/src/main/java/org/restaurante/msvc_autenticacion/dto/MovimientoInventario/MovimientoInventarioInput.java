package org.restaurante.msvc_autenticacion.dto.MovimientoInventario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventarioInput {

    private Double cantidad;
    private String motivo;
    private Long almacenInsumoId;
    private Long tenantId;
    private Long tipoMovimientoId;

}
