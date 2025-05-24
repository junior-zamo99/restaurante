package org.restaurante.msvc_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDTO {
    private Long permisoId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private TenantSimpleDTO tenant;
}
