package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Mesa.MesaDTO;
import org.restaurante.msvc_autenticacion.dto.Mesa.MesaSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Mesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MesaMapper {

    @Autowired
    private TenantMapper tenantMapper;

    public MesaDTO toDto(Mesa mesa) {
        if(mesa == null) {
            return null;
        }

        MesaDTO mesaDTO = new MesaDTO();
        mesaDTO.setMesaId(mesa.getMesaId());

        mesaDTO.setCapacidad(mesa.getCapacidad());
        mesaDTO.setEstado(mesa.getEstado());

        if(mesa.getTenant() != null) {
            mesaDTO.setTenantId(tenantMapper.toSimpleDto(mesa.getTenant()));
        }
        return mesaDTO;


    }

    public MesaSimpleDTO toSimpleDto(Mesa mesa) {
        if(mesa == null) {
            return null;
        }

        return new MesaSimpleDTO(
                mesa.getMesaId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getEstado()
        );
    }




}
