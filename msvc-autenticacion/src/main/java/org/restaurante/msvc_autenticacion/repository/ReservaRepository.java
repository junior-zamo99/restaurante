package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByTenantTenantId(Long tenantId);

    List<Reserva> findByTenantTenantIdAndEstado(Long tenantId, Boolean estado);

    List<Reserva> findByTenantTenantIdAndClienteClienteId(Long tenantId, Long clienteId);

    List<Reserva> findByTenantTenantIdAndMesaMesaId(Long tenantId, Long mesaId);

    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND DATE(r.fechaReserva) = DATE(:fecha)")
    List<Reserva> findByTenantIdAndFechaReserva(@Param("tenantId") Long tenantId, @Param("fecha") LocalDateTime fecha);

    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND r.fechaReserva BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findByTenantIdAndFechaReservaBetween(
            @Param("tenantId") Long tenantId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("SELECT r FROM Reserva r WHERE r.mesa.mesaId = :mesaId AND DATE(r.fechaReserva) = DATE(:fecha) AND r.estado = true")
    List<Reserva> findReservasActivasByMesaAndFecha(@Param("mesaId") Long mesaId, @Param("fecha") LocalDateTime fecha);

    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND DATE(r.fechaReserva) = CURRENT_DATE AND r.estado = true")
    List<Reserva> findReservasActivasDelDiaByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND DATE(r.fechaReserva) = CURRENT_DATE")
    List<Reserva> findReservasDelDiaByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND r.estado = :estado")
    Long countByTenantIdAndEstado(@Param("tenantId") Long tenantId, @Param("estado") Boolean estado);

    boolean existsByMesaMesaIdAndFechaReservaAndEstado(Long mesaId, LocalDateTime fechaReserva, Boolean estado);
}