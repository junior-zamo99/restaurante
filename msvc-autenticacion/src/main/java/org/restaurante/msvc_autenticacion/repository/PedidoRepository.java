package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByTenantTenantId(Long tenantId);
    List<Pedido> findByEstado(Boolean estado);
    List<Pedido> findByTenantTenantIdAndEstado(Long tenantId, Boolean estado);
    List<Pedido> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Pedido> findByTenantTenantIdAndFechaHoraBetween(Long tenantId, LocalDateTime inicio, LocalDateTime fin);

    List<Pedido> findByCuentaMesaCuentaMesaId(Long cuentaMesaId);
    List<Pedido> findByTenantTenantIdAndCuentaMesaCuentaMesaId(Long tenantId, Long cuentaMesaId);
}
