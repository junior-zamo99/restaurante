package org.restaurante.msvc_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolInput {
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private Long tenantId;
    private List<Long> permisoIds;
}
