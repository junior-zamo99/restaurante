package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaDTO;
import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaInput;
import org.restaurante.msvc_autenticacion.service.CuentaMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CuentaMesaController {


    @Autowired
    private CuentaMesaService cuentaMesaService;

    @QueryMapping
    public CuentaMesaDTO cuentaMesaById(@Argument Long id) {
        return cuentaMesaService.findById(id);
    }

    @QueryMapping
    public List<CuentaMesaDTO> cuentaMesasByTenantId(@Argument Long tenantId) {
        return cuentaMesaService.findByTenantId(tenantId);
    }

    @QueryMapping
    public List<CuentaMesaDTO> cuentaMesasByTenantIdAndEstado(@Argument Long tenantId, @Argument String estado) {
        return cuentaMesaService.findByTenantIdAndEstado(tenantId, estado);
    }

    @QueryMapping
    public CuentaMesaDTO cuentaMesaByMesaIdAndEstado(@Argument Long mesaId, @Argument String estado) {
        return cuentaMesaService.findByMesaIdAndEstado(mesaId, estado);
    }

    @QueryMapping
    public List<CuentaMesaDTO> cuentaMesasByTenantIdAndClienteId(@Argument Long tenantId, @Argument Long clienteId) {
        return cuentaMesaService.findByTenantIdAndClienteId(tenantId, clienteId);
    }

    @QueryMapping
    public List<CuentaMesaDTO> cuentasActivasByTenantId(@Argument Long tenantId) {
        return cuentaMesaService.findCuentasActivasByTenantId(tenantId);
    }

    @MutationMapping
    public CuentaMesaDTO createCuentaMesa(@Argument CuentaMesaInput input) {
        return cuentaMesaService.create(input);
    }

    @MutationMapping
    public CuentaMesaDTO updateCuentaMesa(@Argument Long id, @Argument CuentaMesaInput input) {
        return cuentaMesaService.update(id, input);
    }

    @MutationMapping
    public CuentaMesaDTO cerrarCuentaMesa(@Argument Long id) {
        return cuentaMesaService.cerrarCuentaMesa(id);
    }

    @MutationMapping
    public Boolean deleteCuentaMesa(@Argument Long id) {
        return cuentaMesaService.delete(id);
    }

}
