package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.PermisoDTO;
import org.restaurante.msvc_autenticacion.mapper.PermisoMapper;
import org.restaurante.msvc_autenticacion.model.Permiso;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.PermisoRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PermisoMapper permisoMapper;

    public PermisoDTO findById(Long id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado con id: " + id));
        return permisoMapper.toDto(permiso);
    }

    public List<PermisoDTO> findByCodigo(String codigo) {
        return permisoRepository.findByCodigo(codigo).stream()
                .map(permisoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PermisoDTO> findByTenantId(Long tenantId) {
        return permisoRepository.findByTenantTenantId(tenantId).stream()
                .map(permisoMapper::toDto)
                .collect(Collectors.toList());
    }
}
