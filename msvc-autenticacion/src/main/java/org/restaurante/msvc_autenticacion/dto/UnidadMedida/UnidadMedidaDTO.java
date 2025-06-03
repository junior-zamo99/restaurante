package org.restaurante.msvc_autenticacion.dto.UnidadMedida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadMedidaDTO {
    private Long unidadMedidaId;
    private String nombre;
    private String abreviatura;
    private TenantSimpleDTO tenant;
}
