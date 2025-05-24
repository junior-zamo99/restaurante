package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findByEstado(String estado);
}
