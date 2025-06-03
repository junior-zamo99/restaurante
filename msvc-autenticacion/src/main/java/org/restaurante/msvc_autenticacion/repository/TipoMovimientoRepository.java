package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Long> {


}
