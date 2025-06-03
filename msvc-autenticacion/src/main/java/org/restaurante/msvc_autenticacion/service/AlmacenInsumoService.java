package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenDTO;
import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoDTO;
import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoInput;
import org.restaurante.msvc_autenticacion.mapper.AlmacenInsumoMapper;
import org.restaurante.msvc_autenticacion.model.Almacen;
import org.restaurante.msvc_autenticacion.model.AlmacenInsumo;
import org.restaurante.msvc_autenticacion.model.Insumo;
import org.restaurante.msvc_autenticacion.repository.AlmacenInsumoRepository;
import org.restaurante.msvc_autenticacion.repository.AlmacenRepository;
import org.restaurante.msvc_autenticacion.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlmacenInsumoService {

    @Autowired
    private AlmacenInsumoRepository almacenInsumoRepository;
    @Autowired
    private AlmacenRepository almacenRepository;
    @Autowired
    private InsumoRepository insumoRepository;
    @Autowired
    private AlmacenInsumoMapper almacenInsumoMapper;

    public List<AlmacenInsumoDTO> findByAlmacenId(Long almacenId) {
        return almacenInsumoRepository.findByAlmacenAlmacenId(almacenId).stream()
                .map(almacenInsumoMapper::toDto)
                .toList();
    }

    public List<AlmacenInsumoDTO> findByInsumoId(Long insumoId) {
        return almacenInsumoRepository.findByInsumoInsumoId(insumoId).stream()
                .map(almacenInsumoMapper::toDto)
                .toList();
    }

   public AlmacenInsumoDTO findByAlmacenIdAndInsumoId(Long almacenId, Long insumoId) {
        AlmacenInsumo almacenInsumo = almacenInsumoRepository.findByAlmacenAlmacenIdAndInsumoInsumoId(almacenId, insumoId);
        if (almacenInsumo == null) {
            throw new RuntimeException("AlmacenInsumo not found with Almacen ID: " + almacenId + " and Insumo ID: " + insumoId);
        }
        return almacenInsumoMapper.toDto(almacenInsumo);
    }

    public AlmacenInsumoDTO findById(Long id){
        AlmacenInsumo almacenInsumo = almacenInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AlmacenInsumo not found with id: " + id));
        return almacenInsumoMapper.toDto(almacenInsumo);
    }

    @Transactional
    public AlmacenInsumoDTO create(AlmacenInsumoInput input) {
        AlmacenInsumo almacenInsumo = new AlmacenInsumo();
        return saveOrUpdateAlmacenInsumo(almacenInsumo, input);
    }

    @Transactional
    public AlmacenInsumoDTO update(Long id, AlmacenInsumoInput input) {
        AlmacenInsumo almacenInsumo = almacenInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AlmacenInsumo not found with id: " + id));
        return saveOrUpdateAlmacenInsumo(almacenInsumo, input);
    }

    private AlmacenInsumoDTO saveOrUpdateAlmacenInsumo(AlmacenInsumo almacenInsumo, AlmacenInsumoInput input) {
        almacenInsumo.setCantidad(input.getCantidad());

        Almacen almacen = almacenRepository.findById(input.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacen not found with id: " + input.getAlmacenId()));
        almacenInsumo.setAlmacen(almacen);

        Insumo insumo = insumoRepository.findById(input.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo not found with id: " + input.getInsumoId()));
        almacenInsumo.setInsumo(insumo);

        AlmacenInsumo savedAlmacenInsumo = almacenInsumoRepository.save(almacenInsumo);
        return almacenInsumoMapper.toDto(savedAlmacenInsumo);
    }

    public Boolean existsByAlmacenIdAndInsumoId(Long almacenId, Long insumoId) {
        return almacenInsumoRepository.existsByAlmacenAlmacenIdAndInsumoInsumoId(almacenId, insumoId);
    }


}
