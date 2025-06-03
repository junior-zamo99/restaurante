package org.restaurante.msvc_autenticacion.dto.UnidadMedida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadMedidaSimpleDTO {

    private Long unidadMedidaId;
    private String nombre;
    private String abreviatura;
}
