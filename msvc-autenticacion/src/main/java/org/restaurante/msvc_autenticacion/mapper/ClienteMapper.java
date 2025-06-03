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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ClienteDTO toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setClienteId(cliente.getClienteId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDireccion(cliente.getDireccion());
        clienteDTO.setEstado(cliente.getEstado());

        if (cliente.getTenant() != null) {
            clienteDTO.setTenant(tenantMapper.toSimpleDto(cliente.getTenant()));
        }

        if (cliente.getFechaNacimiento() != null) {
            clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento().format(DATE_FORMATTER));
        }

        if (cliente.getCreatedAt() != null) {
            clienteDTO.setCreatedAt(cliente.getCreatedAt().format(DATE_TIME_FORMATTER));
        }

        return clienteDTO;
    }

    public ClienteSimpleDTO toSimpleDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteSimpleDTO(
                cliente.getClienteId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono()
        );
    }


}
