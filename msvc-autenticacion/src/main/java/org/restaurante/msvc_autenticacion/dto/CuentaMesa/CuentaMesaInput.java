package org.restaurante.msvc_autenticacion.dto.CuentaMesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMesaInput {
    private Long mesaId;
    private String estado;
    private String fechaHoraFin;
    private Integer numComensales;
    private Long clienteId;
    private BigDecimal montoTotal;
    private Long tenantId;
}