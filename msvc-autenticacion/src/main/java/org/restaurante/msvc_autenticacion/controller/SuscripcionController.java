package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.SuscripcionDTO;
import org.restaurante.msvc_autenticacion.service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;

    @QueryMapping
    public SuscripcionDTO suscripcionById(@Argument Long id) {
        return suscripcionService.findById(id);
    }

    @QueryMapping
    public List<SuscripcionDTO> suscripciones() {
        return suscripcionService.findAll();
    }
}
