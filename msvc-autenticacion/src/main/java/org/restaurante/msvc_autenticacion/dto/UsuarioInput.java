package org.restaurante.msvc_autenticacion.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInput {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String username;
    private Boolean estado;
    private Long tenantId;
    private List<Long> rolIds;
}
