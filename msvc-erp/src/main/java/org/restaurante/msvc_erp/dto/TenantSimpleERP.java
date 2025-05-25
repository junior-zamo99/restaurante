package org.restaurante.msvc_erp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantSimpleERP {
    private String tenantId;
    private String nombre;
    private String razonSocial;
    private String estado;
}