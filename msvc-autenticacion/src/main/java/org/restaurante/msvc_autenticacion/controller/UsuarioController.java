package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.AuthPayload;
import org.restaurante.msvc_autenticacion.dto.LoginInput;
import org.restaurante.msvc_autenticacion.dto.UsuarioDTO;
import org.restaurante.msvc_autenticacion.dto.UsuarioInput;
import org.restaurante.msvc_autenticacion.service.AuthService;
import org.restaurante.msvc_autenticacion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthService authService;

    @QueryMapping
    public UsuarioDTO usuarioById(@Argument Long id) {
        return usuarioService.findById(id);
    }

    @QueryMapping
    public UsuarioDTO usuarioByUsername(@Argument String username) {
        return usuarioService.findByUsername(username);
    }

    @QueryMapping
    public List<UsuarioDTO> usuariosByTenantId(@Argument Long tenantId) {
        return usuarioService.findByTenantId(tenantId);
    }

    @MutationMapping
    public UsuarioDTO createUsuario(@Argument UsuarioInput input) {
        return usuarioService.create(input);
    }

    @MutationMapping
    public UsuarioDTO updateUsuario(@Argument Long id, @Argument UsuarioInput input) {
        return usuarioService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteUsuario(@Argument Long id) {
        return usuarioService.delete(id);
    }

    @MutationMapping
    public UsuarioDTO assignRolesToUsuario(@Argument Long usuarioId, @Argument List<Long> rolIds) {
        return usuarioService.assignRoles(usuarioId, rolIds);
    }

    @MutationMapping
    public AuthPayload login(@Argument String username, @Argument String password) {
        LoginInput input = new LoginInput(username, password);
        return authService.login(input);
    }
}
