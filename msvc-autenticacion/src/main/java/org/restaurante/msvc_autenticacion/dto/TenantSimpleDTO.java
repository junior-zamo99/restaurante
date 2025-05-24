package org.restaurante.msvc_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantSimpleDTO {
    private Long tenantId;
    private String nombre;
    private String razonSocial;
    private String estado;
}
