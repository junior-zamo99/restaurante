package org.restaurante.msvc_autenticacion.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSimpleDTO {
    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String username;
}
