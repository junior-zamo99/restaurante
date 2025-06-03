package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaDTO;
import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaSimpleDTO;
import org.restaurante.msvc_autenticacion.model.UnidadMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnidadMedidaMapper {

    @Autowired
    private TenantMapper tenantMapper;

    public UnidadMedidaDTO toDto(UnidadMedida unidadMedida){
        if(unidadMedida == null) {
            return null;
        }
        UnidadMedidaDTO unidadMedidaDTO = new UnidadMedidaDTO();
        unidadMedidaDTO.setUnidadMedidaId(unidadMedida.getUnidadMedidaId());
        unidadMedidaDTO.setNombre(unidadMedida.getNombre());
        unidadMedidaDTO.setAbreviatura(unidadMedida.getAbreviatura());

        if (unidadMedida.getTenant() != null) {
            unidadMedidaDTO.setTenant(tenantMapper.toSimpleDto(unidadMedida.getTenant()));
        }
        return unidadMedidaDTO;
    }

    public UnidadMedidaSimpleDTO toSimpleDto(UnidadMedida unidadMedida) {
        if (unidadMedida == null) {
            return null;
        }

        return new UnidadMedidaSimpleDTO(
                unidadMedida.getUnidadMedidaId(),
                unidadMedida.getNombre(),
                unidadMedida.getAbreviatura()
        );
    }


}
