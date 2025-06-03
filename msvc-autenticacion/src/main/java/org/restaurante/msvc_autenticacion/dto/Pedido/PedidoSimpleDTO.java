package org.restaurante.msvc_autenticacion.dto.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoSimpleDTO {

    private Long pedidoId;
    private LocalDateTime fechaHora;
    private Double total;
    private Boolean estado;

}
