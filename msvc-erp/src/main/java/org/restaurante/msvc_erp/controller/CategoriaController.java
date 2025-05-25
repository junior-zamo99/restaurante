package org.restaurante.msvc_erp.controller;

import lombok.RequiredArgsConstructor;
import org.restaurante.msvc_erp.dto.CategoriaDTO;
import org.restaurante.msvc_erp.dto.CategoriaInput;
import org.restaurante.msvc_erp.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @QueryMapping
    public CategoriaDTO categoriaById(@Argument String tenantId, @Argument Long categoriaId) {
        return categoriaService.obtenerCategoriaPorId(tenantId, categoriaId);
    }

    @QueryMapping
    public List<CategoriaDTO> categoriasByTenant(@Argument String tenantId) {
        return categoriaService.findCategoriaByTenantId(tenantId);
    }
    @MutationMapping
    public CategoriaDTO createCategoria(@Argument CategoriaInput input) {
        return categoriaService.crearCategoria(input);
    }
}
