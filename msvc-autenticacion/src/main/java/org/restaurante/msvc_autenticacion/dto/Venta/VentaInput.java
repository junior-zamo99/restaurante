package org.restaurante.msvc_autenticacion.dto.Venta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaInput {
    private Long tenantId;
    private BigDecimal total;
    private Long cuentaMesaId;
    private String estado;
    private String observaciones;
}