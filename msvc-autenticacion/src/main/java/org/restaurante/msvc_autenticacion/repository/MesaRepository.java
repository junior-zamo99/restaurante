package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    List<Mesa> findByTenantTenantId(Long tenantId);
    Mesa findByMesaIdAndTenantTenantId(Long mesaId, Long tenantId);
    List<Mesa> findByEstadoAndTenantTenantId(Boolean estado, Long tenantId);


}
