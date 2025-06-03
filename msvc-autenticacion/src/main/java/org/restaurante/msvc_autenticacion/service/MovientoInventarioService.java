package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.MovimientoInventario.MovimientoInventarioDTO;
import org.restaurante.msvc_autenticacion.dto.MovimientoInventario.MovimientoInventarioInput;
import org.restaurante.msvc_autenticacion.mapper.MovimientoInventarioMapper;
import org.restaurante.msvc_autenticacion.mapper.TipoMovimientoMapper;
import org.restaurante.msvc_autenticacion.model.AlmacenInsumo;
import org.restaurante.msvc_autenticacion.model.MovimientoInventario;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.model.TipoMovimiento;
import org.restaurante.msvc_autenticacion.repository.AlmacenInsumoRepository;
import org.restaurante.msvc_autenticacion.repository.MovimientoInventarioRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.TipoMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovientoInventarioService  {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    private AlmacenInsumoRepository almacenInsumoRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;

    public MovimientoInventarioDTO findById(Long movimientoId){
        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(movimientoId)
                .orElseThrow(() -> new RuntimeException("Movimiento de inventario no encontrado con id: " + movimientoId));
        return movimientoInventarioMapper.toDto(movimientoInventario);
    }


    public List<MovimientoInventarioDTO> findByTenantId(Long tenantId) {
        return movimientoInventarioRepository.findByTenantTenantId(tenantId).stream()
                .map(movimientoInventarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public MovimientoInventarioDTO create(MovimientoInventarioInput input) {
        MovimientoInventario movimientoInventario = new MovimientoInventario();
        return saveOrUpdateMovimientoInventario(movimientoInventario, input);
    }


    private MovimientoInventarioDTO saveOrUpdateMovimientoInventario(MovimientoInventario movimientoInventario, MovimientoInventarioInput input) {
        movimientoInventario.setCantidad(input.getCantidad());
        movimientoInventario.setMotivo(input.getMotivo());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));

        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(input.getTipoMovimientoId())
                .orElseThrow(() -> new RuntimeException("Tipo de movimiento no encontrado con id: " + input.getTipoMovimientoId()));

        AlmacenInsumo almacenInsumo = almacenInsumoRepository.findById(input.getAlmacenInsumoId())
                .orElseThrow(() -> new RuntimeException("Almacen de insumo no encontrado con id: " + input.getAlmacenInsumoId()));

        movimientoInventario.setTenant(tenant);
        movimientoInventario.setTipoMovimiento(tipoMovimiento);
        movimientoInventario.setAlmacenInsumo(almacenInsumo);
        MovimientoInventario savedMovimiento = movimientoInventarioRepository.save(movimientoInventario);
        return movimientoInventarioMapper.toDto(savedMovimiento);



    }


}
