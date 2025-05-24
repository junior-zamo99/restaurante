package org.restaurante.msvc_autenticacion.controller;
import org.restaurante.msvc_autenticacion.dto.RolDTO;
import org.restaurante.msvc_autenticacion.dto.RolInput;
import org.restaurante.msvc_autenticacion.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RolController {

    @Autowired
    private RolService rolService;

    @QueryMapping
    public RolDTO rolById(@Argument Long id) {
        return rolService.findById(id);
    }

    @QueryMapping
    public List<RolDTO> rolesByTenantId(@Argument Long tenantId) {
        return rolService.findByTenantId(tenantId);
    }

    @MutationMapping
    public RolDTO createRol(@Argument RolInput input) {
        return rolService.create(input);
    }

    @MutationMapping
    public RolDTO updateRol(@Argument Long id, @Argument RolInput input) {
        return rolService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteRol(@Argument Long id) {
        return rolService.delete(id);
    }

    @MutationMapping
    public RolDTO assignPermisosToRol(@Argument Long rolId, @Argument List<Long> permisoIds) {
        return rolService.assignPermisos(rolId, permisoIds);
    }

}
