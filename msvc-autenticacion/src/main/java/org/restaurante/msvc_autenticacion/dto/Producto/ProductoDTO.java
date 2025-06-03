package org.restaurante.msvc_autenticacion.dto.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long productoId;
    private String nombre;
    private String nombreCorto;
    private String descripcion;
    private Double precio;
    private CategoriaSimpleDTO categoria;
    private UnidadMedidaSimpleDTO unidadMedida;
    private TenantSimpleDTO tenant;


}
