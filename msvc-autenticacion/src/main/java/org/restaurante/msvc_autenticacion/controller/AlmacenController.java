package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenDTO;
import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenInput;
import org.restaurante.msvc_autenticacion.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;


    @QueryMapping
    public AlmacenDTO almacenById(@Argument Long id) {
        return almacenService.findById(id);
    }

    @QueryMapping
    public List<AlmacenDTO> almacenByTenantId(@Argument Long tenantId) {
        return almacenService.findByTenantId(tenantId);
    }

    @MutationMapping
    public AlmacenDTO createAlmacen(@Argument AlmacenInput input) {
        return almacenService.create(input);
    }
}
