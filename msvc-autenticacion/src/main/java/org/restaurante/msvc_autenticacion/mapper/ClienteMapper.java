package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteDTO;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ClienteMapper {

    @Autowired
    private TenantMapper tenantMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ClienteDTO toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setClienteId(cliente.getClienteId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setUsername(cliente.getUsername());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setEstado(cliente.getEstado());

        if (cliente.getTenant() != null) {
            dto.setTenant(tenantMapper.toSimpleDto(cliente.getTenant()));
        }

        if (cliente.getFechaNacimiento() != null) {
            dto.setFechaNacimiento(cliente.getFechaNacimiento().toLocalDate().format(DATE_FORMATTER));
        }

        if (cliente.getCreatedAt() != null) {
            dto.setCreatedAt(cliente.getCreatedAt().toString());
        }

        return dto;
    }

    public ClienteSimpleDTO toSimpleDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteSimpleDTO dto = new ClienteSimpleDTO();
        dto.setClienteId(cliente.getClienteId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setUsername(cliente.getUsername());

        return dto;
    }

}
