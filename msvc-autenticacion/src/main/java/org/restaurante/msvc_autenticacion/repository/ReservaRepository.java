package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByTenantTenantId(Long tenantId);

    // Métodos para buscar por estado (como String)
    List<Reserva> findByTenantTenantIdAndEstado(Long tenantId, String estado);

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

    // Reservas activas por mesa y fecha
    @Query("SELECT r FROM Reserva r WHERE r.mesa.mesaId = :mesaId AND DATE(r.fechaReserva) = DATE(:fecha) AND r.estado = 'Activa'")
    List<Reserva> findReservasActivasByMesaAndFecha(@Param("mesaId") Long mesaId, @Param("fecha") LocalDateTime fecha);

    // Reservas del día actual
    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND CAST(r.fechaReserva AS date) = CURRENT_DATE")
    List<Reserva> findReservasDelDiaByTenantId(@Param("tenantId") Long tenantId);

    // Reservas activas del día actual
    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND CAST(r.fechaReserva AS date) = CURRENT_DATE AND r.estado = 'Activa'")
    List<Reserva> findReservasActivasDelDiaByTenantId(@Param("tenantId") Long tenantId);



    // Contar por tenant y estado
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.tenant.tenantId = :tenantId AND r.estado = :estado")
    Long countByTenantIdAndEstado(@Param("tenantId") Long tenantId, @Param("estado") String estado);

    // Verificar si existe una reserva activa para una mesa y fecha
    boolean existsByMesaMesaIdAndFechaReservaAndEstado(Long mesaId, LocalDateTime fechaReserva, String estado);

    // Reservas pendientes de confirmación que ya pasaron su límite
    @Query("SELECT r FROM Reserva r WHERE r.confirmada = false AND r.mensajeConfirmacionEnviado = true " +
            "AND r.horaLimiteConfirmacion < :ahora AND r.estado = 'Activa'")
    List<Reserva> findReservasPendientesConfirmacionExpiradas(@Param("ahora") LocalDateTime ahora);

    // Reservas próximas dentro de un rango de horas
    @Query("SELECT r FROM Reserva r WHERE r.tenant.tenantId = :tenantId " +
            "AND r.fechaReserva BETWEEN :ahora AND :limite AND r.estado = 'Activa'")
    List<Reserva> findReservasProximasByHoras(
            @Param("tenantId") Long tenantId,
            @Param("ahora") LocalDateTime ahora,
            @Param("limite") LocalDateTime limite);

    // Encontrar reserva por teléfono del cliente pendiente de confirmación
    @Query("SELECT r FROM Reserva r WHERE r.cliente.telefono = :telefono " +
            "AND r.mensajeConfirmacionEnviado = true AND r.confirmada = false " +
            "AND r.estado = 'Activa' ORDER BY r.fechaReserva DESC")
    List<Reserva> findByClienteTelefonoAndPendienteConfirmacion(@Param("telefono") String telefono);

    // Buscar la reserva pendiente de confirmación más reciente para un cliente
    @Query("SELECT r FROM Reserva r WHERE r.cliente.telefono = :telefono " +
            "AND r.mensajeConfirmacionEnviado = true AND r.confirmada = false " +
            "AND r.estado = 'Activa' ORDER BY r.fechaReserva DESC")
    Optional<Reserva> findLatestPendingByClienteTelefono(@Param("telefono") String telefono);

    // Reservas para recordatorio (próximas N horas)
    @Query("SELECT r FROM Reserva r JOIN r.cliente c " +
            "WHERE r.tenant.tenantId = :tenantId " +
            "AND r.fechaReserva BETWEEN :ahora AND :limite " +
            "AND r.estado = 'Activa' " +
            "AND r.mensajeConfirmacionEnviado = false")
    List<Reserva> findReservasForReminder(
            @Param("tenantId") Long tenantId,
            @Param("ahora") LocalDateTime ahora,
            @Param("limite") LocalDateTime limite);

}