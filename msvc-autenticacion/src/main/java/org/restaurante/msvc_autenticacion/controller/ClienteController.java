package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteDTO;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteInput;
import org.restaurante.msvc_autenticacion.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @QueryMapping
    public ClienteDTO clienteById(@Argument Long id) {
        return clienteService.findById(id);
    }

    @QueryMapping
    public List<ClienteDTO> clientesByTenantId(@Argument Long tenantId) {
        return clienteService.findByTenantId(tenantId);
    }

    @QueryMapping
    public List<ClienteDTO> clientesByTenantIdAndEstado(@Argument Long tenantId, @Argument Boolean estado) {
        return clienteService.findByTenantIdAndEstado(tenantId, estado);
    }

    @QueryMapping
    public ClienteDTO clienteByTenantIdAndEmail(@Argument Long tenantId, @Argument String email) {
        return clienteService.findByTenantIdAndEmail(tenantId, email);
    }

    @QueryMapping
    public ClienteDTO clienteByTenantIdAndTelefono(@Argument Long tenantId, @Argument String telefono) {
        return clienteService.findByTenantIdAndTelefono(tenantId, telefono);
    }



    @MutationMapping
    public ClienteDTO createCliente(@Argument ClienteInput input) {
        return clienteService.create(input);
    }

    @MutationMapping
    public ClienteDTO updateCliente(@Argument Long id, @Argument ClienteInput input) {
        return clienteService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteCliente(@Argument Long id) {
        return clienteService.delete(id);
    }

    @MutationMapping
    public ClienteDTO toggleEstadoCliente(@Argument Long id) {
        return clienteService.toggleEstado(id);
    }

}
