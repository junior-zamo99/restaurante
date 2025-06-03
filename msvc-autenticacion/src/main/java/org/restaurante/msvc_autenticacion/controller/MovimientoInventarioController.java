package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.MovimientoInventario.MovimientoInventarioDTO;
import org.restaurante.msvc_autenticacion.dto.MovimientoInventario.MovimientoInventarioInput;
import org.restaurante.msvc_autenticacion.service.MovientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovimientoInventarioController {

    @Autowired
    private MovientoInventarioService movientoInventarioService;

    @QueryMapping
    public MovimientoInventarioDTO MovimientoInventarioById(@Argument Long id) {
        return movientoInventarioService.findById(id);
    }

    @QueryMapping
    public List<MovimientoInventarioDTO> MovimientoInventarioByTenantId(@Argument Long tenantId) {
        return movientoInventarioService.findByTenantId(tenantId);
    }

    @MutationMapping
    public MovimientoInventarioDTO createMovimientoInventario(@Argument MovimientoInventarioInput input) {
        return movientoInventarioService.create(input);
    }

}
