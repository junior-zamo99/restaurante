package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaDTO;
import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaInput;
import org.restaurante.msvc_autenticacion.mapper.CategoriaMapper;
import org.restaurante.msvc_autenticacion.model.Categoria;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.CategoriaRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + id));
        return categoriaMapper.toDto(categoria);
    }

    public List<CategoriaDTO> findByTenantId(Long tenantId) {
        return categoriaRepository.findByTenantTenantId(tenantId).stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaDTO create(CategoriaInput input) {

        Categoria categoria = new Categoria();
        return saveOrUpdateCategoria(categoria, input);

    }

    private CategoriaDTO saveOrUpdateCategoria(Categoria categoria, CategoriaInput input) {
        categoria.setNombre(input.getNombre());
        categoria.setDescripcion(input.getDescripcion());
        categoria.setEstado(true);

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        categoria.setTenant(tenant);
        Categoria savedCategoria = categoriaRepository.save(categoria);

        return categoriaMapper.toDto(savedCategoria);




    }
}
