package org.restaurante.msvc_autenticacion.dto.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSimpleDTO {

    private Long productoId;
    private String nombre;
    private String nombreCorto;
    private String descripcion;
    private Double precio;


}
