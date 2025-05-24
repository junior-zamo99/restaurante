package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.TenantDTO;
import org.restaurante.msvc_autenticacion.dto.TenantInput;
import org.restaurante.msvc_autenticacion.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @QueryMapping
    public TenantDTO tenantById(@Argument Long id) {
        return tenantService.findById(id);
    }

    @QueryMapping
    public List<TenantDTO> tenants() {
        return tenantService.findAll();
    }

    @QueryMapping
    public List<TenantDTO> tenantsByEstado(@Argument String estado) {
        return tenantService.findByEstado(estado);
    }

    @MutationMapping
    public TenantDTO createTenant(@Argument TenantInput input) {
        return tenantService.create(input);
    }

    @MutationMapping
    public TenantDTO updateTenant(@Argument Long id, @Argument TenantInput input) {
        return tenantService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteTenant(@Argument Long id) {
        return tenantService.delete(id);
    }

}
