package org.restaurante.msvc_autenticacion.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {
    private Long rolId;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private TenantSimpleDTO tenant;
    private List<UsuarioSimpleDTO> usuarios;
    private List<PermisoSimpleDTO> permisos;
    private String createdAt;
}
