package org.restaurante.msvc_autenticacion.dto.PedidoDetalle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalleInput {
    private Integer cantidad;
    private String estado;
    private String notas;
    private Long pedidoId;
    private Long productoId;
}
