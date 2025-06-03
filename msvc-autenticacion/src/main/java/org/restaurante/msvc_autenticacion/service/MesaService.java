package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Mesa.MesaDTO;
import org.restaurante.msvc_autenticacion.dto.Mesa.MesaInput;
import org.restaurante.msvc_autenticacion.mapper.MesaMapper;
import org.restaurante.msvc_autenticacion.model.Mesa;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.MesaRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private MesaMapper mesaMapper;

    @Autowired
    private TenantRepository tenantRepository;

    public MesaDTO findByTenantIdAndMesaId(Long tenantId, Long mesaId) {

        Mesa mesa = mesaRepository.findByMesaIdAndTenantTenantId(mesaId, tenantId);
        if (mesa == null) {
            throw new RuntimeException("Mesa not found with id: " + mesaId + " for tenant: " + tenantId);
        }
        return mesaMapper.toDto(mesa);

    }

    public List<MesaDTO> findByTenantId(Long tenantId) {
        return mesaRepository.findByTenantTenantId(tenantId).stream()
                .map(mesaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MesaDTO> findByEstadoAndTenantId(Boolean estado, Long tenantId) {
        return mesaRepository.findByEstadoAndTenantTenantId(estado, tenantId).stream()
                .map(mesaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MesaDTO create(MesaInput input) {
        Mesa mesa = new Mesa();
        return saveOrUpdateMesa(mesa, input);
    }


    private MesaDTO saveOrUpdateMesa(Mesa mesa, MesaInput input) {
        mesa.setNumero(input.getNumero());
        mesa.setCapacidad(input.getCapacidad());
        mesa.setEstado(input.getEstado());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + input.getTenantId()));
        mesa.setTenant(tenant);
        Mesa savedMesa = mesaRepository.save(mesa);
        return mesaMapper.toDto(savedMesa);

    }


}
