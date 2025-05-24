package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByTenantTenantId(Long tenantId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
