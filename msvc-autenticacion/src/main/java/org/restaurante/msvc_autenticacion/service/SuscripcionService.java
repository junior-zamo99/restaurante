package org.restaurante.msvc_autenticacion.service;
import org.restaurante.msvc_autenticacion.dto.SuscripcionDTO;
import org.restaurante.msvc_autenticacion.mapper.SuscripcionMapper;
import org.restaurante.msvc_autenticacion.model.Suscripcion;
import org.restaurante.msvc_autenticacion.repository.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuscripcionService {

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    public SuscripcionDTO findById(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada con id: " + id));
        return suscripcionMapper.toDto(suscripcion);
    }

    public List<SuscripcionDTO> findAll() {
        return suscripcionRepository.findAll().stream()
                .map(suscripcionMapper::toDto)
                .collect(Collectors.toList());
    }
}
