package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaDTO;
import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaSimpleDTO;
import org.restaurante.msvc_autenticacion.model.CuentaMesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CuentaMesaMapper {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private MesaMapper mesaMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CuentaMesaDTO toDto(CuentaMesa cuentaMesa) {
        if (cuentaMesa == null) {
            return null;
        }

        CuentaMesaDTO cuentaMesaDTO = new CuentaMesaDTO();
        cuentaMesaDTO.setCuentaMesaId(cuentaMesa.getCuentaMesaId());
        cuentaMesaDTO.setEstado(cuentaMesa.getEstado());
        cuentaMesaDTO.setNumComensales(cuentaMesa.getNumComensales());
        cuentaMesaDTO.setMontoTotal(cuentaMesa.getMontoTotal());

        if (cuentaMesa.getMesa() != null) {
            cuentaMesaDTO.setMesa(mesaMapper.toSimpleDto(cuentaMesa.getMesa()));
        }

        if (cuentaMesa.getCliente() != null) {
            cuentaMesaDTO.setCliente(clienteMapper.toSimpleDto(cuentaMesa.getCliente()));
        }

        if (cuentaMesa.getTenant() != null) {
            cuentaMesaDTO.setTenant(tenantMapper.toSimpleDto(cuentaMesa.getTenant()));
        }

        if (cuentaMesa.getFechaHoraIni() != null) {
            cuentaMesaDTO.setFechaHoraIni(cuentaMesa.getFechaHoraIni().format(DATE_TIME_FORMATTER));
        }

        if (cuentaMesa.getFechaHoraFin() != null) {
            cuentaMesaDTO.setFechaHoraFin(cuentaMesa.getFechaHoraFin().format(DATE_TIME_FORMATTER));
        }

        if (cuentaMesa.getCreatedAt() != null) {
            cuentaMesaDTO.setCreatedAt(cuentaMesa.getCreatedAt().format(DATE_TIME_FORMATTER));
        }

        return cuentaMesaDTO;
    }

    public CuentaMesaSimpleDTO toSimpleDto(CuentaMesa cuentaMesa) {
        if (cuentaMesa == null) {
            return null;
        }

        return new CuentaMesaSimpleDTO(
                cuentaMesa.getCuentaMesaId(),
                cuentaMesa.getEstado(),
                cuentaMesa.getNumComensales(),
                cuentaMesa.getMontoTotal()
        );
    }

}
