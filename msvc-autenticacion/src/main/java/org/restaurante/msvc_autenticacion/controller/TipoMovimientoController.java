package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.TipoMovimiento.TipoMovimientoDTO;
import org.restaurante.msvc_autenticacion.service.TipoMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TipoMovimientoController {

    @Autowired
    private TipoMovimientoService tipoMovimientoService;

    @QueryMapping
    public TipoMovimientoDTO tipoMovimientoById(@Argument Long id) {
        return tipoMovimientoService.findById(id);
    }
    @QueryMapping
    public List<TipoMovimientoDTO> tipoMovimientos() {
        return tipoMovimientoService.findAll();
    }

}
