package org.restaurante.msvc_autenticacion.mapper;
import org.restaurante.msvc_autenticacion.dto.UsuarioDTO;
import org.restaurante.msvc_autenticacion.dto.UsuarioSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsuarioMapper {
    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    @Lazy
    private RolMapper rolMapper;

    public UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setEstado(usuario.getEstado());


        if (usuario.getTenant() != null) {
            usuarioDTO.setTenant(tenantMapper.toSimpleDto(usuario.getTenant()));
        }

        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            usuarioDTO.setRoles(usuario.getRoles().stream()
                    .map(rolMapper::toSimpleDto)
                    .collect(Collectors.toList()));
        }

        return usuarioDTO;
    }

    public UsuarioSimpleDTO toSimpleDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioSimpleDTO(
                usuario.getUsuarioId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername()
        );
    }
}
