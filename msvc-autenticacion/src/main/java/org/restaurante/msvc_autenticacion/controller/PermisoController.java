package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.PermisoDTO;
import org.restaurante.msvc_autenticacion.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @QueryMapping
    public PermisoDTO permisoById(@Argument Long id) {
        return permisoService.findById(id);
    }

    @QueryMapping
    public List<PermisoDTO> permisosByCodigo(@Argument String codigo) {
        return permisoService.findByCodigo(codigo);
    }

    @QueryMapping
    public List<PermisoDTO> permisosByTenantId(@Argument Long tenantId) {
        return permisoService.findByTenantId(tenantId);
    }


}

