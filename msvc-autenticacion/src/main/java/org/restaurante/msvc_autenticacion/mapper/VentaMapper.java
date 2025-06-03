package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Venta.VentaDTO;
import org.restaurante.msvc_autenticacion.dto.Venta.VentaSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class VentaMapper {


    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private CuentaMesaMapper cuentaMesaMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public VentaDTO toDto(Venta venta) {
        if (venta == null) {
            return null;
        }

        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setVentaId(venta.getVentaId());
        ventaDTO.setTotal(venta.getTotal());
        ventaDTO.setEstado(venta.getEstado());
        ventaDTO.setObservaciones(venta.getObservaciones());

        if (venta.getTenant() != null) {
            ventaDTO.setTenant(tenantMapper.toSimpleDto(venta.getTenant()));
        }

        if (venta.getCuentaMesa() != null) {
            ventaDTO.setCuentaMesa(cuentaMesaMapper.toSimpleDto(venta.getCuentaMesa()));
        }

        if (venta.getFechaVenta() != null) {
            ventaDTO.setFechaVenta(venta.getFechaVenta().format(DATE_TIME_FORMATTER));
        }

        if (venta.getCreatedAt() != null) {
            ventaDTO.setCreatedAt(venta.getCreatedAt().format(DATE_TIME_FORMATTER));
        }

        return ventaDTO;
    }

    public VentaSimpleDTO toSimpleDto(Venta venta) {
        if (venta == null) {
            return null;
        }

        VentaSimpleDTO ventaSimpleDTO = new VentaSimpleDTO();
        ventaSimpleDTO.setVentaId(venta.getVentaId());
        ventaSimpleDTO.setTotal(venta.getTotal());
        ventaSimpleDTO.setEstado(venta.getEstado());

        if (venta.getFechaVenta() != null) {
            ventaSimpleDTO.setFechaVenta(venta.getFechaVenta().format(DATE_TIME_FORMATTER));
        }

        return ventaSimpleDTO;
    }


}
