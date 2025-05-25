package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaDTO;
import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    @Autowired
    private TenantMapper tenantMapper;

    public CategoriaDTO toDto(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setCategoriaId(categoria.getCategoriaId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        categoriaDTO.setEstado(true);


        if (categoria.getTenant() != null) {
            categoriaDTO.setTenant(tenantMapper.toSimpleDto(categoria.getTenant()));
        }

        return categoriaDTO;
    }

    public CategoriaSimpleDTO toSimpleDto(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return new CategoriaSimpleDTO(
                categoria.getCategoriaId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );


    }
}
