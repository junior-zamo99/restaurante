package org.restaurante.msvc_autenticacion.dto.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaInput {
    private String nombre;
    private String descripcion;
    private Long tenantId;
}
