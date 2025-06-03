package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

    List<Insumo> findByTenantTenantId(Long tenantId);
    List<Insumo> findByCategoriaCategoriaId(Long categoriaId);
    List<Insumo> findByUnidadMedidaUnidadMedidaId(Long unidadMedidaId);
    Insumo findByInsumoIdAndTenantTenantId(Long insumoId, Long tenantId);

}