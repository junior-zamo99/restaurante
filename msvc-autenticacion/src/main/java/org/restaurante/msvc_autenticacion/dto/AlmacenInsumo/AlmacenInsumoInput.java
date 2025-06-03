package org.restaurante.msvc_autenticacion.dto.AlmacenInsumo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenInsumoInput {

    private Long almacenId;
    private Long insumoId;
    private Double cantidad;
    private Long tenantId;

}
