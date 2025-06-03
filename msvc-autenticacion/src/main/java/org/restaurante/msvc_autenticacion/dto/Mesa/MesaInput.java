package org.restaurante.msvc_autenticacion.dto.Mesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaInput {


    private String numero;
    private Integer capacidad;
    private Boolean estado;
    private Long tenantId;



}
