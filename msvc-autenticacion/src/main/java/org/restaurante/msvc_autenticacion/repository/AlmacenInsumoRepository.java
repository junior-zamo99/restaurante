package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.AlmacenInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlmacenInsumoRepository extends JpaRepository<AlmacenInsumo, Long> {

    List<AlmacenInsumo> findByAlmacenAlmacenId(Long almacenId);
    List<AlmacenInsumo> findByInsumoInsumoId(Long insumoId);
    AlmacenInsumo findByAlmacenAlmacenIdAndInsumoInsumoId(Long almacenId, Long insumoId);
    boolean existsByAlmacenAlmacenIdAndInsumoInsumoId(Long almacenId, Long insumoId);

}
