package org.restaurante.msvc_erp.repository;

import org.restaurante.msvc_erp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByTenantId(String tenantId);
    Categoria findByNombreAndTenantId(String nombre, String tenantId);
    Categoria findByCategoriaIdAndTenantId(Long categoriaId, String tenantId);
}
