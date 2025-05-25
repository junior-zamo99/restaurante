package org.restaurante.msvc_erp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long categoriaId;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private TenantSimpleERP tenant;
    private LocalDateTime createdAt;
}