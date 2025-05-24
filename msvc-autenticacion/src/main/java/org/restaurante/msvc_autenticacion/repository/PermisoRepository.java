package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    List<Permiso> findByCodigo(String codigo);
    List<Permiso> findByTenantTenantId(Long tenantId);

}
