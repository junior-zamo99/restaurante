package org.restaurante.msvc_autenticacion.repository;

import org.restaurante.msvc_autenticacion.model.EstadoPedidoDetalle;
import org.restaurante.msvc_autenticacion.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

    List<PedidoDetalle> findByPedidoPedidoId(Long pedidoId);
    List<PedidoDetalle> findByProductoProductoId(Long productoId);
    List<PedidoDetalle> findByEstadoAndTenantTenantId(EstadoPedidoDetalle estado, Long tenantId);
    List<PedidoDetalle> findByEstadoInAndTenantTenantId(List<EstadoPedidoDetalle> estados, Long tenantId);
    List<PedidoDetalle> findByPedidoPedidoIdAndEstadoAndTenantTenantId(Long pedidoId, EstadoPedidoDetalle estado, Long tenantId);


    List<PedidoDetalle> findByTenantTenantIdOrderByPedidoFechaHoraAsc(Long tenantId);
    List<PedidoDetalle> findByEstadoAndTenantTenantIdOrderByPedidoFechaHoraAsc(EstadoPedidoDetalle estado, Long tenantId);
}
