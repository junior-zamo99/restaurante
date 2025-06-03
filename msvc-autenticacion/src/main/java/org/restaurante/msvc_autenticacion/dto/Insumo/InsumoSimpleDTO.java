package org.restaurante.msvc_autenticacion.dto.Insumo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoSimpleDTO {

    private Long insumoId;
    private String nombre;
    private String descripcion;



}
