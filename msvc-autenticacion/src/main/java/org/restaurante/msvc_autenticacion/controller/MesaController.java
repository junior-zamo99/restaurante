package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Mesa.MesaDTO;
import org.restaurante.msvc_autenticacion.dto.Mesa.MesaInput;
import org.restaurante.msvc_autenticacion.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MesaController {

    @Autowired
    private MesaService mesaService;


    @QueryMapping
    public MesaDTO mesaByTenantIdAndMesaId(@Argument Long tenantId,@Argument Long mesaId) {
        return mesaService.findByTenantIdAndMesaId(tenantId, mesaId);
    }
    @QueryMapping
    public List<MesaDTO> mesasByTenantId(@Argument Long tenantId) {
        return mesaService.findByTenantId(tenantId);
    }

    @QueryMapping
    public List<MesaDTO> mesasByTenantIdAndEstado(@Argument Long tenantId, @Argument boolean estado) {
        return mesaService.findByEstadoAndTenantId(estado, tenantId);
    }

    @MutationMapping
    public MesaDTO createMesa(@Argument MesaInput input) {
        return mesaService.create(input);
    }
}
