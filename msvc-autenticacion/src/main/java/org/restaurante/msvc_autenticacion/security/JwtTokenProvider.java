package org.restaurante.msvc_autenticacion.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.restaurante.msvc_autenticacion.model.Cliente;
import org.restaurante.msvc_autenticacion.model.Permiso;
import org.restaurante.msvc_autenticacion.model.Rol;
import org.restaurante.msvc_autenticacion.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret:secretSuperSeguroDe256BitsParaHMACSHA}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")  // 24 horas por defecto
    private long jwtExpirationInMs;

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);


        List<String> authorities = usuario.getRoles().stream()
                .flatMap(rol -> Stream.concat(
                        Stream.of("ROL_" + rol.getNombre().toUpperCase()),
                        rol.getPermisos().stream().map(Permiso::getCodigo)
                ))
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("tenantId", usuario.getTenant().getTenantId())
                .claim("userId", usuario.getUsuarioId())
                .claim("authorities", authorities)
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        Long tenantId = claims.get("tenantId", Long.class);

        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");

        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                new JwtUserDetails(username, userId, tenantId),
                token,
                grantedAuthorities
        );
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String createTokenForCliente(Cliente cliente) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(cliente.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("tenantId", cliente.getTenant().getTenantId())
                .claim("clienteId", cliente.getClienteId())
                .claim("esCliente", true)
                // No incluimos roles o permisos
                .signWith(getSigningKey())
                .compact();
    }
}
