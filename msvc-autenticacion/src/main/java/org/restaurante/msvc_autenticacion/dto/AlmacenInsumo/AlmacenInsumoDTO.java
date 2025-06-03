package org.restaurante.msvc_autenticacion.dto.AlmacenInsumo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Almacen.AlmacenSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.Insumo.InsumoSimpleDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenInsumoDTO {

    private Long almacenInsumoId;
    private AlmacenSimpleDTO almacen;
    private InsumoSimpleDTO insumo;
    private Double cantidad;




}
