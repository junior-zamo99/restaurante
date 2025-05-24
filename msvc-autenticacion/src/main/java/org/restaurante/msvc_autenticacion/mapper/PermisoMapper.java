package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.PermisoDTO;
import org.restaurante.msvc_autenticacion.dto.PermisoSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermisoMapper {
    @Autowired
    private TenantMapper tenantMapper;

    public PermisoDTO toDto(Permiso permiso) {
        if (permiso == null) {
            return null;
        }

        PermisoDTO permisoDTO = new PermisoDTO();
        permisoDTO.setPermisoId(permiso.getPermisoId());
        permisoDTO.setCodigo(permiso.getCodigo());
        permisoDTO.setNombre(permiso.getNombre());
        permisoDTO.setDescripcion(permiso.getDescripcion());

        if (permiso.getTenant() != null) {
            permisoDTO.setTenant(tenantMapper.toSimpleDto(permiso.getTenant()));
        }

        return permisoDTO;
    }

    public PermisoSimpleDTO toSimpleDto(Permiso permiso) {
        if (permiso == null) {
            return null;
        }

        return new PermisoSimpleDTO(
                permiso.getPermisoId(),
                permiso.getCodigo(),
                permiso.getNombre()
        );
    }
}
