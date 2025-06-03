package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {


    List<Venta> findByTenantTenantId(Long tenantId);

    List<Venta> findByTenantTenantIdAndEstado(Long tenantId, String estado);

    List<Venta> findByTenantTenantIdAndCuentaMesaCuentaMesaId(Long tenantId, Long cuentaMesaId);

    List<Venta> findByCuentaMesaCuentaMesaId(Long cuentaMesaId);

    @Query("SELECT v FROM Venta v WHERE v.tenant.tenantId = :tenantId AND v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    List<Venta> findByTenantIdAndFechaVentaBetween(
            @Param("tenantId") Long tenantId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.tenant.tenantId = :tenantId AND v.estado = 'COMPLETADA' AND v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal sumTotalByTenantIdAndFechaBetween(
            @Param("tenantId") Long tenantId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.tenant.tenantId = :tenantId AND v.estado = :estado")
    Long countByTenantIdAndEstado(@Param("tenantId") Long tenantId, @Param("estado") String estado);

    @Query("SELECT v FROM Venta v WHERE v.tenant.tenantId = :tenantId ORDER BY v.fechaVenta DESC")
    List<Venta> findByTenantIdOrderByFechaVentaDesc(@Param("tenantId") Long tenantId);

    // ✅ CORREGIDO: Cambié DATE() por CAST()
    @Query("SELECT v FROM Venta v WHERE v.tenant.tenantId = :tenantId AND CAST(v.fechaVenta AS date) = CURRENT_DATE")
    List<Venta> findVentasDelDiaByTenantId(@Param("tenantId") Long tenantId);

    // ✅ CORREGIDO: Cambié DATE() por CAST()
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.tenant.tenantId = :tenantId AND v.estado = 'COMPLETADA' AND CAST(v.fechaVenta AS date) = CURRENT_DATE")
    BigDecimal sumTotalVentasDelDiaByTenantId(@Param("tenantId") Long tenantId);
}