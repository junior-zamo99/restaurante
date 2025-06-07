package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyHashInputDTO {
    private DatosVentaDTO datosVenta;
    private String hashEsperado;
}