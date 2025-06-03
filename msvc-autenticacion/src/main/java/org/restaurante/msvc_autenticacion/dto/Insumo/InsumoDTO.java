package org.restaurante.msvc_autenticacion.dto.Insumo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Categoria.CategoriaSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.UnidadMedida.UnidadMedidaSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoDTO {

    private Long insumoId;
    private String nombre;
    private String descripcion;
    private UnidadMedidaSimpleDTO unidadMedida;
    private CategoriaSimpleDTO categoria;
    private TenantSimpleDTO tenant;

}
