package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Almacen;
import org.restaurante.msvc_autenticacion.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

    List<Almacen> findByTenantTenantId(Long tenantId);

}
