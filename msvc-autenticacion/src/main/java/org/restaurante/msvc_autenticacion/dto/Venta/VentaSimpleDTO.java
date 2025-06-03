package org.restaurante.msvc_autenticacion.dto.Venta;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaSimpleDTO {
    private Long ventaId;
    private BigDecimal total;
    private String estado;
    private String fechaVenta;
}