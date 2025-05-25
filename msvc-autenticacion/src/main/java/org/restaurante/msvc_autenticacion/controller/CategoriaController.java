package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaDTO;
import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaInput;
import org.restaurante.msvc_autenticacion.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @QueryMapping
    public CategoriaDTO categoriaById(@Argument Long id) {
        return categoriaService.findById(id);
    }

    @QueryMapping
    public List<CategoriaDTO> categoriasByTenantId(@Argument Long tenantId) {
        return categoriaService.findByTenantId(tenantId);
    }

    @MutationMapping
    public CategoriaDTO createCategoria(@Argument CategoriaInput input) {
        return categoriaService.create(input);
    }

}
