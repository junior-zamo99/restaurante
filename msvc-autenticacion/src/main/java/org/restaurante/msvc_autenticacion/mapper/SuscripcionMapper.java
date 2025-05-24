package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.SuscripcionDTO;
import org.restaurante.msvc_autenticacion.model.Suscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SuscripcionMapper {
    @Autowired
    private TenantMapper tenantMapper;

    public SuscripcionDTO toDto(Suscripcion suscripcion) {
        if (suscripcion == null) {
            return null;
        }

        SuscripcionDTO suscripcionDTO = new SuscripcionDTO();
        suscripcionDTO.setSuscripcionId(suscripcion.getSuscripcionId());
        suscripcionDTO.setNombre(suscripcion.getNombre());
        suscripcionDTO.setPrecio(suscripcion.getPrecio());



        return suscripcionDTO;
    }
}
