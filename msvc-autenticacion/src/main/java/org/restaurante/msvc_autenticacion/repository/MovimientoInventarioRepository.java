package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

    List<MovimientoInventario> findByTenantTenantId(Long tenantId);
    MovimientoInventario findByMovimientoIdAndTenantTenantId(Long movimientoId, Long tenantId);



}
