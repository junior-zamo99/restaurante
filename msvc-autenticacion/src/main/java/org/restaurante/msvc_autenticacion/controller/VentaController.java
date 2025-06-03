package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Venta.VentaDTO;
import org.restaurante.msvc_autenticacion.dto.Venta.VentaInput;
import org.restaurante.msvc_autenticacion.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class VentaController {
    @Autowired
    private VentaService ventaService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @QueryMapping
    public VentaDTO ventaById(@Argument Long id) {
        return ventaService.findById(id);
    }

    @QueryMapping
    public List<VentaDTO> ventasByTenantId(@Argument Long tenantId) {
        return ventaService.findByTenantId(tenantId);
    }

    @QueryMapping
    public List<VentaDTO> ventasByTenantIdAndEstado(@Argument Long tenantId, @Argument String estado) {
        return ventaService.findByTenantIdAndEstado(tenantId, estado);
    }

    @QueryMapping
    public List<VentaDTO> ventasByCuentaMesaId(@Argument Long cuentaMesaId) {
        return ventaService.findByCuentaMesaId(cuentaMesaId);
    }

    @QueryMapping
    public List<VentaDTO> ventasByTenantIdOrderByFecha(@Argument Long tenantId) {
        return ventaService.findByTenantIdOrderByFecha(tenantId);
    }

    @QueryMapping
    public List<VentaDTO> ventasDelDiaByTenantId(@Argument Long tenantId) {
        return ventaService.findVentasDelDiaByTenantId(tenantId);
    }

    @QueryMapping
    public BigDecimal totalVentasByTenantIdAndFecha(@Argument Long tenantId, @Argument String fechaInicio, @Argument String fechaFin) {
        try {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DATE_TIME_FORMATTER);
            LocalDateTime fin = LocalDateTime.parse(fechaFin, DATE_TIME_FORMATTER);
            return ventaService.getTotalVentasByTenantIdAndFecha(tenantId, inicio, fin);
        } catch (Exception e) {
            throw new RuntimeException("Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm:ss");
        }
    }

    @QueryMapping
    public BigDecimal totalVentasDelDiaByTenantId(@Argument Long tenantId) {
        return ventaService.getTotalVentasDelDiaByTenantId(tenantId);
    }

    @MutationMapping
    public VentaDTO createVenta(@Argument VentaInput input) {
        return ventaService.create(input);
    }

    @MutationMapping
    public VentaDTO updateVenta(@Argument Long id, @Argument VentaInput input) {
        return ventaService.update(id, input);
    }

    @MutationMapping
    public VentaDTO completarVenta(@Argument Long id) {
        return ventaService.completarVenta(id);
    }

    @MutationMapping
    public VentaDTO cancelarVenta(@Argument Long id, @Argument String motivo) {
        return ventaService.cancelarVenta(id, motivo);
    }

    @MutationMapping
    public Boolean deleteVenta(@Argument Long id) {
        return ventaService.delete(id);
    }

}
