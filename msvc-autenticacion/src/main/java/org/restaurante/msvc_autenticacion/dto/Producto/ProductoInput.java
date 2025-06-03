package org.restaurante.msvc_autenticacion.dto.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInput {

    private String nombre;
    private String nombreCorto;
    private String descripcion;
    private Double precio;
    private Long categoriaId;
    private Long unidadMedidaId;
    private Long tenantId;

}
