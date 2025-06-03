package org.restaurante.msvc_autenticacion.dto.Mesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesaDTO {

    private Long mesaId;
    private String numero;
    private Integer capacidad;
    private Boolean estado;
    private TenantSimpleDTO tenantId;


}
