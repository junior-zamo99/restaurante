package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleDTO;
import org.restaurante.msvc_autenticacion.mapper.PedidoDetalleMapper;
import org.restaurante.msvc_autenticacion.model.EstadoPedidoDetalle;
import org.restaurante.msvc_autenticacion.model.PedidoDetalle;
import org.restaurante.msvc_autenticacion.repository.PedidoDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaService {

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoDetalleMapper pedidoDetalleMapper;


    public List<PedidoDetalleDTO> findByEstadoAndTenant(EstadoPedidoDetalle estado, Long tenantId) {
        return pedidoDetalleRepository.findByEstadoAndTenantTenantId(estado, tenantId).stream()
                .map(pedidoDetalleMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<PedidoDetalleDTO> obtenerComandaActivaPorTenant(Long tenantId) {
        List<EstadoPedidoDetalle> estadosActivos = Arrays.asList(
                EstadoPedidoDetalle.EN_COMANDA,
                EstadoPedidoDetalle.PREPARANDO
        );

        return pedidoDetalleRepository.findByEstadoInAndTenantTenantId(estadosActivos, tenantId).stream()
                .map(pedidoDetalleMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<PedidoDetalleDTO> obtenerListosParaEntregaPorTenant(Long tenantId) {
        return findByEstadoAndTenant(EstadoPedidoDetalle.LISTO, tenantId);
    }


    @Transactional
    public Boolean enviarPedidoAComanda(Long pedidoId, Long tenantId) {
        try {
            List<PedidoDetalle> detalles = pedidoDetalleRepository.findByPedidoPedidoId(pedidoId);


            for (PedidoDetalle detalle : detalles) {
                if (!detalle.getTenant().getTenantId().equals(tenantId)) {
                    throw new RuntimeException("Pedido no pertenece al tenant especificado");
                }

                if (detalle.getEstado() == EstadoPedidoDetalle.PENDIENTE) {
                    detalle.setEstado(EstadoPedidoDetalle.EN_COMANDA);
                }
            }

            pedidoDetalleRepository.saveAll(detalles);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Transactional
    public PedidoDetalleDTO cambiarEstado(Long pedidoDetalleId, EstadoPedidoDetalle nuevoEstado, Long tenantId) {
        PedidoDetalle detalle = pedidoDetalleRepository.findById(pedidoDetalleId)
                .orElseThrow(() -> new RuntimeException("PedidoDetalle no encontrado"));


        if (!detalle.getTenant().getTenantId().equals(tenantId)) {
            throw new RuntimeException("PedidoDetalle no pertenece al tenant especificado");
        }

        detalle.setEstado(nuevoEstado);
        PedidoDetalle savedDetalle = pedidoDetalleRepository.save(detalle);

        return pedidoDetalleMapper.toDto(savedDetalle);
    }


    @Transactional
    public PedidoDetalleDTO marcarComoListo(Long pedidoDetalleId, Long tenantId) {
        return cambiarEstado(pedidoDetalleId, EstadoPedidoDetalle.LISTO, tenantId);
    }

    @Transactional
    public PedidoDetalleDTO marcarComoEntregado(Long pedidoDetalleId, Long tenantId) {
        return cambiarEstado(pedidoDetalleId, EstadoPedidoDetalle.ENTREGADO, tenantId);
    }

}
