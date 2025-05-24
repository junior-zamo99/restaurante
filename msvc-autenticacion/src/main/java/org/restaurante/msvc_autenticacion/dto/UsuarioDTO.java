package org.restaurante.msvc_autenticacion.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String username;
    private Boolean estado;
    private TenantSimpleDTO tenant;
    private List<RolSimpleDTO> roles;
    private String createdAt;
}
