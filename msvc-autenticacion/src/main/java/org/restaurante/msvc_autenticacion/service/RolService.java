package org.restaurante.msvc_autenticacion.service;


import org.restaurante.msvc_autenticacion.dto.RolDTO;
import org.restaurante.msvc_autenticacion.dto.RolInput;
import org.restaurante.msvc_autenticacion.mapper.RolMapper;
import org.restaurante.msvc_autenticacion.model.Permiso;
import org.restaurante.msvc_autenticacion.model.Rol;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.PermisoRepository;
import org.restaurante.msvc_autenticacion.repository.RolRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolMapper rolMapper;

    public RolDTO findById(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
        return rolMapper.toDto(rol);
    }

    public List<RolDTO> findByTenantId(Long tenantId) {
        return rolRepository.findByTenantTenantId(tenantId).stream()
                .map(rolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RolDTO create(RolInput input) {
        Rol rol = new Rol();
        return saveOrUpdateRol(rol, input);
    }

    @Transactional
    public RolDTO update(Long id, RolInput input) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
        return saveOrUpdateRol(rol, input);
    }

    private RolDTO saveOrUpdateRol(Rol rol, RolInput input) {
        rol.setNombre(input.getNombre());
        rol.setDescripcion(input.getDescripcion());
        rol.setEstado(input.getEstado());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        rol.setTenant(tenant);

        if (input.getPermisoIds() != null && !input.getPermisoIds().isEmpty()) {
            Set<Permiso> permisos = new HashSet<>(permisoRepository.findAllById(input.getPermisoIds()));
            rol.setPermisos(permisos);
        }

        Rol savedRol = rolRepository.save(rol);
        return rolMapper.toDto(savedRol);
    }

    @Transactional
    public RolDTO assignPermisos(Long rolId, List<Long> permisoIds) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + rolId));

        Set<Permiso> permisos = new HashSet<>(permisoRepository.findAllById(permisoIds));
        rol.setPermisos(permisos);

        Rol savedRol = rolRepository.save(rol);
        return rolMapper.toDto(savedRol);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
