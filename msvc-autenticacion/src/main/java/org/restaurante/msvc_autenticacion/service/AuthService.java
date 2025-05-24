package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.AuthPayload;
import org.restaurante.msvc_autenticacion.dto.LoginInput;
import org.restaurante.msvc_autenticacion.dto.UsuarioDTO;
import org.restaurante.msvc_autenticacion.mapper.UsuarioMapper;
import org.restaurante.msvc_autenticacion.model.Usuario;
import org.restaurante.msvc_autenticacion.repository.UsuarioRepository;
import org.restaurante.msvc_autenticacion.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public AuthPayload login(LoginInput input) {
        Usuario usuario = usuarioRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado: " + input.getUsername()));

        // Cambia este mensaje de error para ser más específico
        if (!passwordEncoder.matches(input.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta para usuario: " + input.getUsername());
        }

        // Cambia este mensaje de error para ser más específico
        if (!usuario.getEstado()) {
            throw new RuntimeException("Usuario inactivo: " + input.getUsername());
        }

        // El resto se queda igual
        String token = jwtTokenProvider.createToken(usuario);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        return new AuthPayload(token, usuarioDTO);
    }

}
