package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.TipoMovimiento.TipoMovimientoDTO;
import org.restaurante.msvc_autenticacion.mapper.TipoMovimientoMapper;
import org.restaurante.msvc_autenticacion.model.TipoMovimiento;
import org.restaurante.msvc_autenticacion.repository.TipoMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoMovimientoService {

    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    private TipoMovimientoMapper tipoMovimientoMapper;

    public TipoMovimientoDTO findById(Long id){
        TipoMovimiento tipoMovimiento = (TipoMovimiento) tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de movimiento no encontrado con id: " + id));
        return tipoMovimientoMapper.toDto(tipoMovimiento);
    }

    public List<TipoMovimientoDTO> findAll() {
        return tipoMovimientoRepository.findAll().stream()
                .map(tipoMovimientoMapper::toDto)
                .collect(Collectors.toList());
    }

}
