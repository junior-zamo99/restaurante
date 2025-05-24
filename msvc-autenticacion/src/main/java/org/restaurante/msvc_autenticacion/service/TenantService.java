package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.TenantDTO;
import org.restaurante.msvc_autenticacion.dto.TenantInput;
import org.restaurante.msvc_autenticacion.mapper.TenantMapper;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private TenantMapper tenantMapper;

    public TenantDTO findById(Long id){
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + id));
        return tenantMapper.toDto(tenant);
    }

    public List<TenantDTO> findAll() {
        return tenantRepository.findAll().stream()
                .map(tenantMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TenantDTO> findByEstado(String estado) {
        return tenantRepository.findByEstado(estado).stream()
                .map(tenantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TenantDTO create(TenantInput input) {
        Tenant tenant = new Tenant();
        return saveOrUpdateTenant(tenant, input);
    }

    @Transactional
    public TenantDTO update(Long id, TenantInput input) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + id));
        return saveOrUpdateTenant(tenant, input);
    }


    private TenantDTO saveOrUpdateTenant(Tenant tenant, TenantInput input) {
        tenant.setCiudad(input.getCiudad());
        tenant.setNombre(input.getNombre());
        tenant.setRazonSocial(input.getRazonSocial());
        tenant.setNit(input.getNit());
        tenant.setEstado(input.getEstado());
        tenant.setFechaRegistro(input.getFechaRegistro() != null ? input.getFechaRegistro() : LocalDate.now());
        tenant.setFechaActivacion(input.getFechaActivacion());
        tenant.setFechaVencimiento(input.getFechaVencimiento());
        tenant.setContactoNombre(input.getContactoNombre());
        tenant.setDireccion(input.getDireccion());
        tenant.setContactoEmail(input.getContactoEmail());
        tenant.setContactoTelefono(input.getContactoTelefono());

        if (input.getSuscripcionId() != null) {
            tenant.setSuscripcion(suscripcionRepository.findById(input.getSuscripcionId())
                    .orElseThrow(() -> new RuntimeException("Suscripción no encontrada con id: " + input.getSuscripcionId())));
        }

        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(savedTenant);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
