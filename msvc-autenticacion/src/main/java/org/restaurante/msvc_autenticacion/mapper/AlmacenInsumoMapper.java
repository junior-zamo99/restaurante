package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoDTO;
import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoSimpleDTO;
import org.restaurante.msvc_autenticacion.model.AlmacenInsumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AlmacenInsumoMapper {

    @Lazy
    @Autowired
    private AlmacenMapper almacenMapper;
    @Lazy
    @Autowired
    private InsumoMapper insumoMapper;

    public AlmacenInsumoDTO toDto(AlmacenInsumo almacenInsumo){
        if(almacenInsumo == null) {
            return null;
        }

        AlmacenInsumoDTO almacenInsumoDTO = new AlmacenInsumoDTO();
        almacenInsumoDTO.setAlmacenInsumoId(almacenInsumo.getAlmacenInsumoId());
        almacenInsumoDTO.setCantidad(almacenInsumo.getCantidad());

        if (almacenInsumo.getAlmacen() != null) {
            almacenInsumoDTO.setAlmacen(almacenMapper.toSimpleDto(almacenInsumo.getAlmacen()));
        }
        if (almacenInsumo.getInsumo() != null) {
            almacenInsumoDTO.setInsumo(insumoMapper.toSimpleDto(almacenInsumo.getInsumo()));
        }
        return almacenInsumoDTO;

    }

    public AlmacenInsumoSimpleDTO toSimpleDto(AlmacenInsumo almacenInsumo) {
        if (almacenInsumo == null) {
            return null;
        }

        return new AlmacenInsumoSimpleDTO(
                almacenInsumo.getAlmacenInsumoId(),
                almacenInsumo.getCantidad()
        );
    }

}
