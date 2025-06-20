package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

   List<Producto> findByTenantTenantId(Long tenantId);
   Producto findByProductoIdAndTenantTenantId(Long productoId, Long tenantId);


}
