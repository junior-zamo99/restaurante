package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {
    List<Rol> findByTenantTenantId(Long tenantId);
    List<Rol> findByNombre(String nombre);


}
