package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoDTO;
import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoInput;
import org.restaurante.msvc_autenticacion.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class InsumoController {

   @Autowired
    private InsumoService insumoService;

   @QueryMapping
    public InsumoDTO insumoById(@Argument Long id) {
         return insumoService.findById(id);
    }
    @QueryMapping
    public List<InsumoDTO> insumosByTenantId(@Argument Long tenantId) {
        return insumoService.findByTenantId(tenantId);
    }
    @MutationMapping
    public InsumoDTO createInsumo(@Argument InsumoInput input) {
        return insumoService.create(input);
    }

}
