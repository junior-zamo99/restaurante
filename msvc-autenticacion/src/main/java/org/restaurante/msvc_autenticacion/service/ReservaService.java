package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Reserva.ReservaDTO;
import org.restaurante.msvc_autenticacion.dto.Reserva.ReservaInput;
import org.restaurante.msvc_autenticacion.mapper.ReservaMapper;
import org.restaurante.msvc_autenticacion.model.Cliente;
import org.restaurante.msvc_autenticacion.model.Mesa;
import org.restaurante.msvc_autenticacion.model.Reserva;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.repository.ClienteRepository;
import org.restaurante.msvc_autenticacion.repository.MesaRepository;
import org.restaurante.msvc_autenticacion.repository.ReservaRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReservaDTO findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
        return reservaMapper.toDto(reserva);
    }

    public List<ReservaDTO> findByTenantId(Long tenantId) {
        return reservaRepository.findByTenantTenantId(tenantId).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> findByTenantIdAndEstado(Long tenantId, Boolean estado) {
        return reservaRepository.findByTenantTenantIdAndEstado(tenantId, estado).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> findByTenantIdAndClienteId(Long tenantId, Long clienteId) {
        return reservaRepository.findByTenantTenantIdAndClienteClienteId(tenantId, clienteId).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> findByTenantIdAndMesaId(Long tenantId, Long mesaId) {
        return reservaRepository.findByTenantTenantIdAndMesaMesaId(tenantId, mesaId).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> findByTenantIdAndFechaReserva(Long tenantId, String fecha) {
        try {
            LocalDateTime fechaReserva = LocalDateTime.parse(fecha + " 00:00:00", DATE_TIME_FORMATTER);
            return reservaRepository.findByTenantIdAndFechaReserva(tenantId, fechaReserva).stream()
                    .map(reservaMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Formato de fecha inválido. Use el formato yyyy-MM-dd");
        }
    }

    public List<ReservaDTO> findReservasDelDiaByTenantId(Long tenantId) {
        return reservaRepository.findReservasDelDiaByTenantId(tenantId).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> findReservasActivasDelDiaByTenantId(Long tenantId) {
        return reservaRepository.findReservasActivasDelDiaByTenantId(tenantId).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservaDTO create(ReservaInput input) {
        if (input.getTenantId() == null) {
            throw new RuntimeException("El tenantId es requerido");
        }

        if (input.getMesaId() == null) {
            throw new RuntimeException("El mesaId es requerido");
        }

        if (input.getClienteId() == null) {
            throw new RuntimeException("El clienteId es requerido");
        }

        if (input.getCantidadPersonas() == null || input.getCantidadPersonas() <= 0) {
            throw new RuntimeException("La cantidad de personas debe ser mayor a 0");
        }

        // Validar disponibilidad de mesa para reservas activas
        try {
            LocalDateTime fechaReserva = LocalDateTime.parse(input.getFechaReserva() + " 00:00:00", DATE_TIME_FORMATTER);

            if (reservaRepository.existsByMesaMesaIdAndFechaReservaAndEstado(input.getMesaId(), fechaReserva, true)) {
                throw new RuntimeException("La mesa ya tiene una reserva activa para esta fecha");
            }
        } catch (Exception e) {
            if (e.getMessage().contains("mesa ya tiene una reserva activa")) {
                throw e;
            }
            throw new RuntimeException("Formato de fecha inválido. Use el formato yyyy-MM-dd");
        }

        Reserva reserva = new Reserva();
        return saveOrUpdateReserva(reserva, input);
    }

    @Transactional
    public ReservaDTO update(Long id, ReservaInput input) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        return saveOrUpdateReserva(reserva, input);
    }

    private ReservaDTO saveOrUpdateReserva(Reserva reserva, ReservaInput input) {
        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));
        reserva.setTenant(tenant);

        Mesa mesa = mesaRepository.findById(input.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + input.getMesaId()));
        reserva.setMesa(mesa);

        Cliente cliente = clienteRepository.findById(input.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + input.getClienteId()));
        reserva.setCliente(cliente);

        reserva.setCantidadPersonas(input.getCantidadPersonas());
        reserva.setObservaciones(input.getObservaciones());

        if (input.getEstado() != null) {
            reserva.setEstado(input.getEstado());
        }

        if (input.getFechaReserva() != null && !input.getFechaReserva().isEmpty()) {
            try {
                reserva.setFechaReserva(LocalDateTime.parse(input.getFechaReserva() + " 00:00:00", DATE_TIME_FORMATTER));
            } catch (Exception e) {
                throw new RuntimeException("Formato de fecha inválido. Use el formato yyyy-MM-dd");
            }
        }

        if (input.getHora() != null && !input.getHora().isEmpty()) {
            try {
                reserva.setHora(LocalTime.parse(input.getHora(), TIME_FORMATTER));
            } catch (Exception e) {
                throw new RuntimeException("Formato de hora inválido. Use el formato HH:mm");
            }
        }

        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO activarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setEstado(true);
        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO desactivarReserva(Long id, String motivo) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setEstado(false);
        if (motivo != null && !motivo.isEmpty()) {
            reserva.setObservaciones(reserva.getObservaciones() != null ?
                    reserva.getObservaciones() + " - CANCELADA: " + motivo : "CANCELADA: " + motivo);
        }

        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO toggleEstadoReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setEstado(!reserva.getEstado());
        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public Boolean delete(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
