package org.restaurante.msvc_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthPayload {
    private String token;
    private UsuarioDTO usuario;
    private ClienteDTO cliente;

    public AuthPayload(String token, UsuarioDTO usuario) {
        this.token = token;
        this.usuario = usuario;
        this.cliente = null;
    }
}
