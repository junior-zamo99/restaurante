package org.restaurante.msvc_autenticacion.dto.Almacen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenInput {

    private String nombre;
    private String ubicacion;
    private Long tenantId;

}
