package org.restaurante.msvc_autenticacion.dto.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long categoriaId;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private TenantSimpleDTO tenant;
}
