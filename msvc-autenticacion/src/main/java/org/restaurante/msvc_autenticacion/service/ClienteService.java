package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteDTO;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteInput;
import org.restaurante.msvc_autenticacion.mapper.ClienteMapper;
import org.restaurante.msvc_autenticacion.model.Cliente;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.ClienteRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClienteMapper clienteMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return clienteMapper.toDto(cliente);
    }

    public ClienteDTO findByUsername(String username) {
        Cliente cliente = clienteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con username: " + username));
        return clienteMapper.toDto(cliente);
    }

    public List<ClienteDTO> findByTenantId(Long tenantId) {
        return clienteRepository.findByTenantTenantId(tenantId).stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> findByTenantIdAndEstado(Long tenantId, Boolean estado) {
        return clienteRepository.findByTenantTenantIdAndEstado(tenantId, estado).stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClienteDTO findByTenantIdAndEmail(Long tenantId, String email) {
        Cliente cliente = clienteRepository.findByTenantTenantIdAndEmail(tenantId, email);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con email: " + email);
        }
        return clienteMapper.toDto(cliente);
    }

    public ClienteDTO findByTenantIdAndTelefono(Long tenantId, String telefono) {
        Cliente cliente = clienteRepository.findByTenantTenantIdAndTelefono(tenantId, telefono);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con teléfono: " + telefono);
        }
        return clienteMapper.toDto(cliente);
    }

    @Transactional
    public ClienteDTO create(ClienteInput input) {
        if (input.getTenantId() == null) {
            throw new RuntimeException("El tenantId es requerido");
        }

        if (input.getEmail() != null && clienteRepository.existsByTenantTenantIdAndEmail(input.getTenantId(), input.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con ese email en este tenant");
        }

        if (input.getTelefono() != null && clienteRepository.existsByTenantTenantIdAndTelefono(input.getTenantId(), input.getTelefono())) {
            throw new RuntimeException("Ya existe un cliente con ese teléfono en este tenant");
        }

        if (input.getUsername() != null && clienteRepository.existsByUsername(input.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        Cliente cliente = new Cliente();
        return saveOrUpdateCliente(cliente, input);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteInput input) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        if (input.getEmail() != null && !cliente.getEmail().equals(input.getEmail()) &&
                clienteRepository.existsByTenantTenantIdAndEmail(input.getTenantId(), input.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con ese email en este tenant");
        }

        if (input.getTelefono() != null && !cliente.getTelefono().equals(input.getTelefono()) &&
                clienteRepository.existsByTenantTenantIdAndTelefono(input.getTenantId(), input.getTelefono())) {
            throw new RuntimeException("Ya existe un cliente con ese teléfono en este tenant");
        }

        if (input.getUsername() != null && !cliente.getUsername().equals(input.getUsername()) &&
                clienteRepository.existsByUsername(input.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        return saveOrUpdateCliente(cliente, input);
    }

    private ClienteDTO saveOrUpdateCliente(Cliente cliente, ClienteInput input) {
        cliente.setNombre(input.getNombre());
        cliente.setApellido(input.getApellido());
        cliente.setEmail(input.getEmail());
        cliente.setTelefono(input.getTelefono());
        cliente.setDireccion(input.getDireccion());
        cliente.setEstado(input.getEstado());

        if (input.getUsername() != null) {
            cliente.setUsername(input.getUsername());
        }

        if (input.getPassword() != null && !input.getPassword().isEmpty()) {
            cliente.setPassword(passwordEncoder.encode(input.getPassword()));
        }

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        cliente.setTenant(tenant);

        if (input.getFechaNacimiento() != null && !input.getFechaNacimiento().isEmpty()) {
            try {
                cliente.setFechaNacimiento(LocalDateTime.parse(input.getFechaNacimiento() + " 00:00:00",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                throw new RuntimeException("Formato de fecha inválido. Use el formato yyyy-MM-dd");
            }
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(savedCliente);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public ClienteDTO toggleEstado(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        cliente.setEstado(!cliente.getEstado());
        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(savedCliente);
    }
}