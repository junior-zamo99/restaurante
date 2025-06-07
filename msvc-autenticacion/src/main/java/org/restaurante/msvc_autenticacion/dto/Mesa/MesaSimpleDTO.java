package org.restaurante.msvc_autenticacion.dto.Mesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaSimpleDTO {

    private Long mesaId;
    private Integer numero;
    private Integer capacidad;
    private Boolean estado;



}
