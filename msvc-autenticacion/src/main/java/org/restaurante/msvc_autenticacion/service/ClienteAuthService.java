package org.restaurante.msvc_autenticacion.service;


import org.restaurante.msvc_autenticacion.dto.AuthPayload;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteDTO;
import org.restaurante.msvc_autenticacion.mapper.ClienteMapper;
import org.restaurante.msvc_autenticacion.model.Cliente;
import org.restaurante.msvc_autenticacion.repository.ClienteRepository;
import org.restaurante.msvc_autenticacion.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteAuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ClienteMapper clienteMapper;

    public AuthPayload loginCliente(String username, String password) {
        Cliente cliente = clienteRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(password, cliente.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String token = tokenProvider.createTokenForCliente(cliente);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        return new AuthPayload(token, null, clienteDTO);
    }
}