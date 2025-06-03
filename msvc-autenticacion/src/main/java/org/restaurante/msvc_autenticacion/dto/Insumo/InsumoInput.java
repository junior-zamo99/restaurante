package org.restaurante.msvc_autenticacion.dto.Insumo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoInput {

    private String nombre;
    private String descripcion;
    private Long unidadMedidaId;
    private Long categoriaId;
    private Long tenantId;

}
