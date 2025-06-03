package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.TipoMovimiento.TipoMovimientoDTO;
import org.restaurante.msvc_autenticacion.dto.TipoMovimiento.TipoMovimientoSimpleDTO;
import org.restaurante.msvc_autenticacion.model.TipoMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoMovimientoMapper {


    @Autowired
    private TenantMapper tenantMapper;

    public TipoMovimientoDTO toDto(TipoMovimiento tipoMovimiento){
        if(tipoMovimiento == null){
            return null;
        }

        TipoMovimientoDTO tipoMovimientoDTO = new TipoMovimientoDTO();
        tipoMovimientoDTO.setTipoMovimientoId(tipoMovimiento.getTipoMovimientoId());
        tipoMovimientoDTO.setNombre(tipoMovimiento.getNombre());
        tipoMovimientoDTO.setDescripcion(tipoMovimiento.getDescripcion());
        tipoMovimientoDTO.setAfectaInventario(tipoMovimiento.isAfectaInventario());

        return tipoMovimientoDTO;

    }

    public TipoMovimientoSimpleDTO toSimpleDto(TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == null) {
            return null;
        }

        return new TipoMovimientoSimpleDTO(
                tipoMovimiento.getTipoMovimientoId(),
                tipoMovimiento.getNombre(),
                tipoMovimiento.getDescripcion(),
                tipoMovimiento.isAfectaInventario()
        );
    }




}
