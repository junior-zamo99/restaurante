package org.restaurante.msvc_autenticacion.dto.PedidoDetalle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Pedido.PedidoSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.Producto.ProductoSimpleDTO;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalleDTO {
    private Long pedidoDetalleId;
    private Integer cantidad;
    private BigDecimal subtotal;
    private String estado;
    private String notas;
    private PedidoSimpleDTO pedido;
    private ProductoSimpleDTO producto;
    private String createdAt;
}
