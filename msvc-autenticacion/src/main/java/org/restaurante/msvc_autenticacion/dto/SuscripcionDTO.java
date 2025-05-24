package org.restaurante.msvc_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionDTO {
    private Long suscripcionId;
    private String nombre;
    private BigDecimal precio;
    private List<TenantSimpleDTO> tenants;
}
