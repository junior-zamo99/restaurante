package org.restaurante.msvc_erp.service;

import lombok.RequiredArgsConstructor;
import org.restaurante.msvc_erp.dto.CategoriaDTO;
import org.restaurante.msvc_erp.dto.CategoriaInput;
import org.restaurante.msvc_erp.dto.TenantInfoDTO;
import org.restaurante.msvc_erp.dto.TenantSimpleERP;
import org.restaurante.msvc_erp.model.Categoria;
import org.restaurante.msvc_erp.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository CategoriaRepository;
    private final TenantService tenantService;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findCategoriaByTenantId(String tenantId) {
        List<Categoria> categorias = CategoriaRepository.findByTenantId(tenantId);

        TenantInfoDTO tenantInfo = tenantService.getTenantInfo(tenantId);
        if (tenantInfo == null) {
            throw new RuntimeException("Tenant not found");
        }

        return mapToDTOList(categorias, tenantInfo);
    }

    @Transactional
    public CategoriaDTO crearCategoria(CategoriaInput input) {

        if (!tenantService.existeTenant(input.getTenantId())) {
            throw new RuntimeException("Tenant not found");
        }

        TenantInfoDTO tenantInfo = tenantService.getTenantInfo(input.getTenantId());

        Categoria categoria = new Categoria();
        categoria.setNombre(input.getNombre());
        categoria.setDescripcion(input.getDescripcion());
        categoria.setEstado(input.getEstado() != null ? input.getEstado() : true);
        categoria.setTenantId(input.getTenantId());
        categoria.setCreatedAt(LocalDate.now());

        Categoria saved = CategoriaRepository.save(categoria);
        return mapToDTO(saved, tenantInfo);
    }

    @Transactional(readOnly = true)
    public CategoriaDTO obtenerCategoriaPorId(String tenantId, Long categoriaId) {

        TenantInfoDTO tenantInfo = tenantService.getTenantInfo(tenantId);
        if (tenantInfo == null) {
            throw new RuntimeException("Tenant not found");
        }

        Categoria categoria = CategoriaRepository.findByCategoriaIdAndTenantId(categoriaId, tenantId);
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada");
        }


        if (!categoria.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Categoría no pertenece al tenant");
        }

        return mapToDTO(categoria, tenantInfo);
    }



    private CategoriaDTO mapToDTO(Categoria categoria, TenantInfoDTO tenantInfo) {
        TenantSimpleERP tenantDTO = TenantSimpleERP.builder()
                .tenantId(tenantInfo.getTenantId())
                .nombre(tenantInfo.getNombre())
                .razonSocial(tenantInfo.getRazonSocial())
                .estado(tenantInfo.getEstado())
                .build();

        return CategoriaDTO.builder()
                .categoriaId(categoria.getCategoriaId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .estado(categoria.getEstado())
                .tenant(tenantDTO)
                .build();
    }
    private List<CategoriaDTO> mapToDTOList(List<Categoria> categorias, TenantInfoDTO tenantInfo) {
        return categorias.stream()
                .map(categoria -> mapToDTO(categoria, tenantInfo))
                .collect(Collectors.toList());
    }
}
