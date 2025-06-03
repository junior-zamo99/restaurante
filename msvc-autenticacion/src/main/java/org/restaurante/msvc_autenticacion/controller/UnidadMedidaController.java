package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaDTO;
import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaInput;
import org.restaurante.msvc_autenticacion.service.UnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UnidadMedidaController {

    @Autowired
    private UnidadMedidaService unidadMedidaService;

    @QueryMapping
    public UnidadMedidaDTO unidadMedidaById(Long id) {
        return unidadMedidaService.findById(id);
    }

    @QueryMapping
    public List<UnidadMedidaDTO> unidadesMedidaByTenantId(Long tenantId) {
        return unidadMedidaService.findByTenantId(tenantId);
    }

    @MutationMapping
    public UnidadMedidaDTO createUnidadMedida(@Argument UnidadMedidaInput input) {
        return unidadMedidaService.create(input);
    }

}
