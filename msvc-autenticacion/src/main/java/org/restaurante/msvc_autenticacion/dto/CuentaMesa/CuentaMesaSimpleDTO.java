package org.restaurante.msvc_autenticacion.dto.CuentaMesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMesaSimpleDTO {
    private Long cuentaMesaId;
    private String estado;
    private Integer numComensales;
    private BigDecimal montoTotal;
}