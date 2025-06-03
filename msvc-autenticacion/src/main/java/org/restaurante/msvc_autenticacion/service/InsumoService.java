package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoDTO;
import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoInput;
import org.restaurante.msvc_autenticacion.mapper.InsumoMapper;
import org.restaurante.msvc_autenticacion.model.Categoria;
import org.restaurante.msvc_autenticacion.model.Insumo;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.model.UnidadMedida;
import org.restaurante.msvc_autenticacion.repository.CategoriaRepository;
import org.restaurante.msvc_autenticacion.repository.InsumoRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private InsumoMapper insumoMapper;

    public InsumoDTO findById(Long id){
        Insumo insumo= insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo not found with id: " + id));
        return insumoMapper.toDto(insumo);
    }

    public List<InsumoDTO> findByTenantId(Long tenantId) {
       return insumoRepository.findByTenantTenantId(tenantId).stream()
                .map(insumoMapper::toDto)
               .collect(Collectors.toList());

    }

    @Transactional
    public InsumoDTO create(InsumoInput input) {

        Insumo insumo = new Insumo();
        return saveOrUpdateInsumo(insumo, input);
    }

    private InsumoDTO saveOrUpdateInsumo(Insumo insumo, InsumoInput input) {
        insumo.setNombre(input.getNombre());
        insumo.setDescripcion(input.getDescripcion());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + input.getTenantId()));
        insumo.setTenant(tenant);

        Categoria categoria = categoriaRepository.findById(input.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria not found with id: " + input.getCategoriaId()));
        insumo.setCategoria(categoria);

        UnidadMedida unidadMedida = unidadMedidaRepository.findById(input.getUnidadMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidad de Medida not found with id: " + input.getUnidadMedidaId()));
        insumo.setUnidadMedida(unidadMedida);

        Insumo savedInsumo = insumoRepository.save(insumo);
        return insumoMapper.toDto(savedInsumo);


    }

}
