package org.restaurante.msvc_autenticacion.security;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserDetails {
    private String username;
    private Long userId;
    private Long tenantId;
}
