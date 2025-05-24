package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.TenantDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class TenantMapper {
    @Autowired
    @Lazy
    private SuscripcionMapper suscripcionMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    public TenantDTO toDto(Tenant tenant) {
        if (tenant == null) {
            return null;
        }

        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setTenantId(tenant.getTenantId());
        tenantDTO.setCiudad(tenant.getCiudad());
        tenantDTO.setNombre(tenant.getNombre());
        tenantDTO.setRazonSocial(tenant.getRazonSocial());
        tenantDTO.setNit(tenant.getNit());
        tenantDTO.setEstado(tenant.getEstado());

        if (tenant.getFechaRegistro() != null) {
            tenantDTO.setFechaRegistro(tenant.getFechaRegistro());
        }

        if (tenant.getFechaActivacion() != null) {
            tenantDTO.setFechaActivacion(tenant.getFechaActivacion());
        }

        if (tenant.getFechaVencimiento() != null) {
            tenantDTO.setFechaVencimiento(tenant.getFechaVencimiento());
        }

        tenantDTO.setContactoNombre(tenant.getContactoNombre());
        tenantDTO.setDireccion(tenant.getDireccion());
        tenantDTO.setContactoEmail(tenant.getContactoEmail());
        tenantDTO.setContactoTelefono(tenant.getContactoTelefono());


        if (tenant.getSuscripcion() != null) {
            tenantDTO.setSuscripcion(suscripcionMapper.toDto(tenant.getSuscripcion()));
        }

        return tenantDTO;
    }

    public TenantSimpleDTO toSimpleDto(Tenant tenant) {
        if (tenant == null) {
            return null;
        }

        return new TenantSimpleDTO(
                tenant.getTenantId(),
                tenant.getNombre(),
                tenant.getRazonSocial(),
                tenant.getEstado()
        );
    }
}
