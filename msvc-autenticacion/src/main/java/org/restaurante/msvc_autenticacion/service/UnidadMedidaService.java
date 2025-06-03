package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaDTO;
import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaInput;
import org.restaurante.msvc_autenticacion.mapper.UnidadMedidaMapper;
import org.restaurante.msvc_autenticacion.model.UnidadMedida;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadMedidaService {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;

    public UnidadMedidaDTO findById(Long id) {
       UnidadMedida unidadMedida = unidadMedidaRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada con id: " + id));
         return unidadMedidaMapper.toDto(unidadMedida);
    }

    public List<UnidadMedidaDTO> findByTenantId(Long tenantId) {
        return unidadMedidaRepository.findByTenantTenantId(tenantId).stream()
                .map(unidadMedidaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UnidadMedidaDTO create(UnidadMedidaInput input) {
        UnidadMedida unidadMedida = new UnidadMedida();
        return saveOrUpdateUnidadMedida(unidadMedida, input);
    }





    private UnidadMedidaDTO saveOrUpdateUnidadMedida(UnidadMedida unidadMedida, UnidadMedidaInput input) {
        unidadMedida.setNombre(input.getNombre());
        unidadMedida.setAbreviatura(input.getAbreviatura());

        System.out.println("TenantId recibido: " + input.getTenantId());
        System.out.println("Tipo de TenantId: " + (input.getTenantId() != null ? input.getTenantId().getClass().getName() : "null"));

        var tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        unidadMedida.setTenant(tenant);

        UnidadMedida savedUnidadMedida = unidadMedidaRepository.save(unidadMedida);
        return unidadMedidaMapper.toDto(savedUnidadMedida);
    }

}
