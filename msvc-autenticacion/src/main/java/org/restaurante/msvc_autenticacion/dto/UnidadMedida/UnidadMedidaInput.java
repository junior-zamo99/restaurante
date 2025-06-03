package org.restaurante.msvc_autenticacion.dto.UnidadMedida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadMedidaInput {

    private String nombre;
    private String abreviatura;
    private Long tenantId;

}
