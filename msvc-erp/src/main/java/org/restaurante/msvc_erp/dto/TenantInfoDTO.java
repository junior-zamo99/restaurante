package org.restaurante.msvc_erp.dto;

import lombok.Data;

@Data
public class TenantInfoDTO {
    private String tenantId;
    private String nombre;
    private String razonSocial;
    private String estado;
}
