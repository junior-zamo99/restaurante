package org.restaurante.msvc_autenticacion.mapper;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.restaurante.msvc_autenticacion.dto.RolDTO;
import org.restaurante.msvc_autenticacion.dto.RolSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RolMapper {
    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private PermisoMapper permisoMapper;

    @Autowired
    @Lazy
    private UsuarioMapper usuarioMapper;

    public RolDTO toDto(Rol rol) {
        if (rol == null) {
            return null;
        }

        RolDTO rolDTO = new RolDTO();
        rolDTO.setRolId(rol.getRolId());
        rolDTO.setNombre(rol.getNombre());
        rolDTO.setDescripcion(rol.getDescripcion());
        rolDTO.setEstado(rol.getEstado());


        if (rol.getTenant() != null) {
            rolDTO.setTenant(tenantMapper.toSimpleDto(rol.getTenant()));
        }

        if (rol.getPermisos() != null && !rol.getPermisos().isEmpty()) {
            rolDTO.setPermisos(rol.getPermisos().stream()
                    .map(permisoMapper::toSimpleDto)
                    .collect(Collectors.toList()));
        }

        if (rol.getUsuarios() != null && !rol.getUsuarios().isEmpty()) {
            rolDTO.setUsuarios(rol.getUsuarios().stream()
                    .map(usuarioMapper::toSimpleDto)
                    .collect(Collectors.toList()));
        }

        return rolDTO;
    }

    public RolSimpleDTO toSimpleDto(Rol rol) {
        if (rol == null) {
            return null;
        }

        return new RolSimpleDTO(
                rol.getRolId(),
                rol.getNombre(),
                rol.getEstado()
        );
    }
}
