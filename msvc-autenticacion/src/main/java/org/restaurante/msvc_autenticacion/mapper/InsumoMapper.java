package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoDTO;
import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsumoMapper {

    @Autowired
    private CategoriaMapper categoriaMapper;
    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;
    @Autowired
    private TenantMapper tenantMapper;

    public InsumoDTO toDto(Insumo insumo){
        if (insumo == null) {
            return null;
        }

        InsumoDTO insumoDTO = new InsumoDTO();
        insumoDTO.setInsumoId(insumo.getInsumoId());
        insumoDTO.setNombre(insumo.getNombre());
        insumoDTO.setDescripcion(insumo.getDescripcion());

        if(insumo.getTenant() != null) {
            insumoDTO.setTenant(tenantMapper.toSimpleDto(insumo.getTenant()));
        }
        if(insumo.getUnidadMedida() != null) {
            insumoDTO.setUnidadMedida(unidadMedidaMapper.toSimpleDto(insumo.getUnidadMedida()));
        }
        if(insumo.getCategoria() != null) {
            insumoDTO.setCategoria(categoriaMapper.toSimpleDto(insumo.getCategoria()));
        }
        return insumoDTO;
    }

    public InsumoSimpleDTO toSimpleDto(Insumo insumo) {
        if (insumo == null) {
            return null;
        }

        return new InsumoSimpleDTO(
                insumo.getInsumoId(),
                insumo.getNombre(),
                insumo.getDescripcion()
        );

    }

}
