package org.restaurante.msvc_autenticacion.dto.MovimientoInventario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.AlmacenInsumo.AlmacenInsumoSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TipoMovimiento.TipoMovimientoSimpleDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventarioDTO {

    private Long movimientoId;
    private Double cantidad;
    private String motivo;
    private AlmacenInsumoSimpleDTO almacenInsumo;
    private TenantSimpleDTO tenant;
    private TipoMovimientoSimpleDTO tipoMovimiento;





}
