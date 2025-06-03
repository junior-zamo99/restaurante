package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenInput;
import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoDTO;
import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoInput;
import org.restaurante.msvc_autenticacion.service.AlmacenInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AlmacenInsumoController {

    @Autowired
    private AlmacenInsumoService almacenInsumoService;


    @QueryMapping
    public List<AlmacenInsumoDTO> getAlmacenInsumosByAlmacenId(@Argument Long almacenId) {
        return almacenInsumoService.findByAlmacenId(almacenId);
    }
    @QueryMapping
    public List<AlmacenInsumoDTO> getAlmacenInsumosByInsumoId(@Argument Long insumoId) {
        return almacenInsumoService.findByInsumoId(insumoId);
    }
    @QueryMapping
    public AlmacenInsumoDTO getAlmacenInsumoByAlmacenIdAndInsumoId(@Argument Long almacenId, Long insumoId) {
        return almacenInsumoService.findByAlmacenIdAndInsumoId(almacenId, insumoId);
    }
    @QueryMapping
    public AlmacenInsumoDTO getAlmacenInsumoById(@Argument Long id) {
        return almacenInsumoService.findById(id);
    }

    @MutationMapping
    public AlmacenInsumoDTO createAlmacenInsumo(@Argument AlmacenInsumoInput input) {
        return almacenInsumoService.create(input);
    }

    @MutationMapping
    public AlmacenInsumoDTO updateAlmacenInsumo(@Argument Long id, @Argument AlmacenInsumoInput input) {
        return almacenInsumoService.update(id, input);
    }


}
