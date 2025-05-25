package org.restaurante.msvc_erp.dto;

import lombok.Data;

@Data
public class CategoriaInput {
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private String tenantId;
}