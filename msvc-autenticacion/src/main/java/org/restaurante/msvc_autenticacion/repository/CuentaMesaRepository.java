package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.CuentaMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CuentaMesaRepository extends JpaRepository<CuentaMesa, Long> {
    List<CuentaMesa> findByTenantTenantId(Long tenantId);

    List<CuentaMesa> findByTenantTenantIdAndEstado(Long tenantId, String estado);

    CuentaMesa findByMesaMesaIdAndEstado(Long mesaId, String estado);

    List<CuentaMesa> findByTenantTenantIdAndMesaMesaId(Long tenantId, Long mesaId);

    List<CuentaMesa> findByTenantTenantIdAndClienteClienteId(Long tenantId, Long clienteId);

    @Query("SELECT cm FROM CuentaMesa cm WHERE cm.tenant.tenantId = :tenantId AND cm.fechaHoraIni BETWEEN :fechaInicio AND :fechaFin")
    List<CuentaMesa> findByTenantIdAndFechaHoraIniBetween(
            @Param("tenantId") Long tenantId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("SELECT cm FROM CuentaMesa cm WHERE cm.tenant.tenantId = :tenantId AND cm.estado = 'ACTIVA'")
    List<CuentaMesa> findCuentasActivasByTenantId(@Param("tenantId") Long tenantId);

    boolean existsByMesaMesaIdAndEstado(Long mesaId, String estado);

    @Query("SELECT COUNT(cm) FROM CuentaMesa cm WHERE cm.tenant.tenantId = :tenantId AND cm.estado = :estado")
    Long countByTenantIdAndEstado(@Param("tenantId") Long tenantId, @Param("estado") String estado);
}
