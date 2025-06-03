package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.MovimientoInventario.MovimientoInventarioDTO;
import org.restaurante.msvc_autenticacion.dto.MovimientoInventario.MovimientoInventarioSimpleDTO;
import org.restaurante.msvc_autenticacion.model.MovimientoInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovimientoInventarioMapper {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private AlmacenInsumoMapper almacenInsumoMapper;

    @Autowired
    private TipoMovimientoMapper tipoMovimientoMapper;

    public MovimientoInventarioDTO toDto(MovimientoInventario movimientoInventario){

        if(movimientoInventario == null) {
            return null;
        }

        MovimientoInventarioDTO movimientoInventarioDTO = new MovimientoInventarioDTO();
        movimientoInventarioDTO.setMovimientoId(movimientoInventario.getMovimientoId());
        movimientoInventarioDTO.setCantidad(movimientoInventario.getCantidad());
        movimientoInventarioDTO.setMotivo(movimientoInventario.getMotivo());

        if(movimientoInventario.getAlmacenInsumo() != null) {
            movimientoInventarioDTO.setAlmacenInsumo(almacenInsumoMapper.toSimpleDto(movimientoInventario.getAlmacenInsumo()));
        }

        if(movimientoInventario.getTenant() != null) {
            movimientoInventarioDTO.setTenant(tenantMapper.toSimpleDto(movimientoInventario.getTenant()));
        }

        if(movimientoInventario.getTipoMovimiento() != null) {
            movimientoInventarioDTO.setTipoMovimiento(tipoMovimientoMapper.toSimpleDto(movimientoInventario.getTipoMovimiento()));
        }

        return movimientoInventarioDTO;

    }

    public MovimientoInventarioSimpleDTO toSimpleDto(MovimientoInventario movimientoInventario) {

        if(movimientoInventario == null) {
            return null;
        }

        return new MovimientoInventarioSimpleDTO(
                movimientoInventario.getMovimientoId(),
                movimientoInventario.getCantidad(),
                movimientoInventario.getMotivo()
        );

    }

}
