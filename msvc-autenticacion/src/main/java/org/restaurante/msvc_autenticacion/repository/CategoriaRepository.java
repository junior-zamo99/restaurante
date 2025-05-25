package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByTenantTenantId(Long tenantId);

}
