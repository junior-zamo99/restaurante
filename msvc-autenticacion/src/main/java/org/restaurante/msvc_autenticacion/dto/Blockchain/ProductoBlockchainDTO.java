package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoBlockchainDTO {
    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private Double precio;
    private Double subtotal;

}
