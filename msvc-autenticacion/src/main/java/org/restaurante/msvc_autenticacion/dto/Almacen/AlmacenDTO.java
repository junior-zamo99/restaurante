package org.restaurante.msvc_autenticacion.dto.Almacen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenDTO {

    private Long almacenId;
    private String nombre;
    private String ubicacion;
    private TenantSimpleDTO tenant;



}
