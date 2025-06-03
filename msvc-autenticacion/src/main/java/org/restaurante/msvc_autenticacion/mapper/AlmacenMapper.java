package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenDTO;
import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Almacen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlmacenMapper {

    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private AlmacenInsumoMapper almacenInsumoMapper;
    @Autowired
    private CategoriaMapper categoriaMapper;
    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;

    public AlmacenDTO toDto(Almacen almacen) {
        if (almacen == null) {
            return null;
        }

        AlmacenDTO almacenDTO = new AlmacenDTO();
        almacenDTO.setAlmacenId(almacen.getAlmacenId());
        almacenDTO.setNombre(almacen.getNombre());
        almacenDTO.setUbicacion(almacen.getUbicacion());

        if (almacen.getTenant() != null) {
            almacenDTO.setTenant(tenantMapper.toSimpleDto(almacen.getTenant()));
        }
        return  almacenDTO;
    }

    public AlmacenSimpleDTO toSimpleDto(Almacen almacen) {
        if (almacen == null) {
            return null;
        }

        return new AlmacenSimpleDTO(
                almacen.getAlmacenId(),
                almacen.getNombre(),
                almacen.getUbicacion()
        );
    }

}
