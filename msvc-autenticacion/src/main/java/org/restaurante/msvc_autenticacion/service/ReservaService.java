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

import java.time.LocalDate;
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

    // Constantes para los estados
    public static final String ESTADO_ACTIVA = "Activa";
    public static final String ESTADO_CANCELADA = "Cancelada";
    public static final String ESTADO_NOSHOW = "NoShow";

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

    // Método modificado para trabajar con estado como String
    public List<ReservaDTO> findByTenantIdAndEstado(Long tenantId, String estado) {
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

            if (reservaRepository.existsByMesaMesaIdAndFechaReservaAndEstado(input.getMesaId(), fechaReserva, ESTADO_ACTIVA)) {
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

        // Manejo del estado como String
        if (input.getEstado() != null) {
            // Validar que el estado sea uno de los permitidos
            if (!ESTADO_ACTIVA.equals(input.getEstado()) &&
                    !ESTADO_CANCELADA.equals(input.getEstado()) &&
                    !ESTADO_NOSHOW.equals(input.getEstado())) {
                throw new RuntimeException("Estado no válido. Use: Activa, Cancelada o NoShow");
            }
            reserva.setEstado(input.getEstado());
        } else if (reserva.getEstado() == null) {
            // Estado por defecto
            reserva.setEstado(ESTADO_ACTIVA);
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

        // NUEVO: Inicialización de campos de confirmación

        if (reserva.getMensajeConfirmacionEnviado() == null) {
            reserva.setMensajeConfirmacionEnviado(false);
        }


        if (reserva.getReservaId() == null && reserva.getConfirmada() == null) {
            reserva.setConfirmada(false);
        }

        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO activarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setEstado(ESTADO_ACTIVA);
        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO cancelarReserva(Long id, String motivo) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setEstado(ESTADO_CANCELADA);
        if (motivo != null && !motivo.isEmpty()) {
            reserva.setObservaciones(reserva.getObservaciones() != null ?
                    reserva.getObservaciones() + " - CANCELADA: " + motivo : "CANCELADA: " + motivo);
        }

        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO marcarNoShow(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setEstado(ESTADO_NOSHOW);



        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    // Método modificado para alternar entre estados Activa y Cancelada
    @Transactional
    public ReservaDTO toggleEstadoReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        // Alternamos entre Activa y Cancelada
        if (ESTADO_ACTIVA.equals(reserva.getEstado())) {
            reserva.setEstado(ESTADO_CANCELADA);
        } else {
            reserva.setEstado(ESTADO_ACTIVA);
        }

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

    // Nuevos métodos para gestionar el flujo de confirmación

    @Transactional
    public ReservaDTO enviarRecordatorio(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setMensajeConfirmacionEnviado(true);
        reserva.setMensajeConfirmacionEnviadoEn(LocalDateTime.now());
        // Establecer 6 horas como límite para confirmación
        reserva.setHoraLimiteConfirmacion(LocalDateTime.now().plusHours(6));

        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    @Transactional
    public ReservaDTO confirmarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        reserva.setConfirmada(true);

        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
    }

    // Método para encontrar reservas que necesitan recordatorio
    @Transactional(readOnly = true)
    public List<ReservaDTO> findReservasForReminder(Long tenantId, Integer horas) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusHours(horas);

        // Obtener todas las reservas activas que necesitan recordatorio
        List<Reserva> todasLasReservasActivas = reservaRepository
                .findByTenantTenantIdAndEstado(tenantId, "Activa");

        // Filtrar manualmente por fecha, hora y campo de recordatorio
        List<Reserva> reservasParaRecordatorio = todasLasReservasActivas.stream()
                .filter(r -> {
                    // Verificar que no se haya enviado recordatorio
                    if (r.getMensajeConfirmacionEnviado()) {
                        return false;
                    }

                    // Comparar solo la fecha (sin hora)
                    LocalDate fechaReserva = r.getFechaReserva().toLocalDate();
                    LocalDate fechaHoy = ahora.toLocalDate();
                    LocalDate fechaLimite = limite.toLocalDate();

                    // La fecha debe estar entre hoy y el límite
                    if (fechaReserva.isBefore(fechaHoy) || fechaReserva.isAfter(fechaLimite)) {
                        return false;
                    }

                    // Si es para hoy, verificar que la hora sea posterior a la actual
                    if (fechaReserva.equals(fechaHoy)) {
                        return r.getHora().isAfter(ahora.toLocalTime());
                    }

                    return true;
                })
                .collect(Collectors.toList());

        return reservasParaRecordatorio.stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    // Método para cancelar reservas expiradas sin confirmación
    @Transactional
    public List<ReservaDTO> cancelarReservasExpiradas() {
        List<Reserva> reservasExpiradas = reservaRepository.findReservasPendientesConfirmacionExpiradas(LocalDateTime.now());

        List<Reserva> reservasCanceladas = reservasExpiradas.stream()
                .map(reserva -> {
                    reserva.setEstado(ESTADO_CANCELADA);
                    reserva.setObservaciones((reserva.getObservaciones() != null ?
                            reserva.getObservaciones() + " - " : "") + "Cancelada automáticamente: No confirmada dentro del tiempo límite");
                    return reservaRepository.save(reserva);
                })
                .collect(Collectors.toList());

        return reservasCanceladas.stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    // Método para buscar reserva por teléfono del cliente pendiente de confirmación
    public ReservaDTO findLatestPendingByClienteTelefono(String telefono) {
        return reservaRepository.findLatestPendingByClienteTelefono(telefono)
                .map(reservaMapper::toDto)
                .orElse(null);
    }

    public List<ReservaDTO> getReservasHoyPendientesDeConfirmacion(Long tenantId) {
        return reservaRepository.findPendientesDeConfirmacionHoy(tenantId).stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }
}