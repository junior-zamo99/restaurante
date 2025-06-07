package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.UsuarioDTO;
import org.restaurante.msvc_autenticacion.dto.UsuarioInput;
import org.restaurante.msvc_autenticacion.mapper.UsuarioMapper;
import org.restaurante.msvc_autenticacion.model.Rol;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.model.Usuario;
import org.restaurante.msvc_autenticacion.repository.RolRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioMapper usuarioMapper;


    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO findByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
        return usuarioMapper.toDto(usuario);
    }

    public List<UsuarioDTO> findByTenantId(Long tenantId) {
        return usuarioRepository.findByTenantTenantId(tenantId).stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO create(UsuarioInput input) {
        if (usuarioRepository.existsByUsername(input.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        return saveOrUpdateUsuario(usuario, input);
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioInput input) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (!usuario.getUsername().equals(input.getUsername()) &&
                usuarioRepository.existsByUsername(input.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (!usuario.getEmail().equals(input.getEmail()) &&
                usuarioRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        return saveOrUpdateUsuario(usuario, input);
    }


    private UsuarioDTO saveOrUpdateUsuario(Usuario usuario, UsuarioInput input) {
        usuario.setNombre(input.getNombre());
        usuario.setApellido(input.getApellido());
        usuario.setEmail(input.getEmail());
        usuario.setTelefono(input.getTelefono());
        usuario.setUsername(input.getUsername());
        usuario.setEstado(true);

        if (input.getPassword() != null && !input.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(input.getPassword()));
        }

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        usuario.setTenant(tenant);

        if (input.getRolIds() != null && !input.getRolIds().isEmpty()) {
            Set<Rol> roles = new HashSet<>(rolRepository.findAllById(input.getRolIds()));
            usuario.setRoles(roles);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(savedUsuario);
    }

    @Transactional
    public UsuarioDTO assignRoles(Long usuarioId, List<Long> rolIds) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(rolIds));
        usuario.setRoles(roles);

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(savedUsuario);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
