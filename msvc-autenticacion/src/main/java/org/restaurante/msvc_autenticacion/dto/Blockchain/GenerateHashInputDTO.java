package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateHashInputDTO {

    private Long ventaId;
    private Long tenantId;
    private DatosVentaDTO datosVenta;


}
