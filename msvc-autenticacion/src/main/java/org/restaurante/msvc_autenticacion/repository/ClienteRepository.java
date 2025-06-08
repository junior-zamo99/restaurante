package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByTenantTenantId(Long tenantId);
    List<Cliente> findByTenantTenantIdAndEstado(Long tenantId, Boolean estado);
    Cliente findByTenantTenantIdAndEmail(Long tenantId, String email);
    Cliente findByTenantTenantIdAndTelefono(Long tenantId, String telefono);
    boolean existsByTenantTenantIdAndEmail(Long tenantId, String email);
    boolean existsByTenantTenantIdAndTelefono(Long tenantId, String telefono);


    Optional<Cliente> findByUsername(String username);
    boolean existsByUsername(String username);
}