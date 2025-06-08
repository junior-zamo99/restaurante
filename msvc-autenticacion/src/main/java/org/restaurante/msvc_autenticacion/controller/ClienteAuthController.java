package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.AuthPayload;
import org.restaurante.msvc_autenticacion.service.ClienteAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ClienteAuthController {

    @Autowired
    private ClienteAuthService clienteAuthService;

    @MutationMapping
    public AuthPayload loginCliente(@Argument String username, @Argument String password) {
        return clienteAuthService.loginCliente(username, password);
    }
}