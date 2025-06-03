package org.restaurante.msvc_autenticacion.repository;


import org.restaurante.msvc_autenticacion.model.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    List<UnidadMedida> findByTenantTenantId(Long tenantId);
    UnidadMedida findByUnidadMedidaIdAndTenantTenantId(Long unidadMedidaId, Long tenantId);
}
