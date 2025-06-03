package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenDTO;
import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenInput;
import org.restaurante.msvc_autenticacion.mapper.AlmacenMapper;
import org.restaurante.msvc_autenticacion.model.Almacen;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.AlmacenRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlmacenService  {

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private AlmacenRepository almacenRepository;
    @Autowired
    private AlmacenMapper almacenMapper;

    public AlmacenDTO findById(Long id){
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacen not found with id: " + id));
        return almacenMapper.toDto(almacen);
    }

    public List<AlmacenDTO> findByTenantId(Long tenantId) {
        return almacenRepository.findByTenantTenantId(tenantId).stream()
                .map(almacenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlmacenDTO create(AlmacenInput input) {
        Almacen almacen = new Almacen();
        return saveOrUpdateAlmacen(almacen, input);
    }

    private AlmacenDTO saveOrUpdateAlmacen(Almacen almacen, AlmacenInput input) {
        almacen.setNombre(input.getNombre());
        almacen.setUbicacion(input.getUbicacion());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + input.getTenantId()));
        almacen.setTenant(tenant);
        Almacen savedAlmacen = almacenRepository.save(almacen);
        return almacenMapper.toDto(savedAlmacen);
    }
}
