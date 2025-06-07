package org.restaurante.msvc_autenticacion.dto.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleInput;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoInput {

    private String notas;
    private Boolean estado;
    private Long tenantId;
    private Long cuentaMesaId;
    private List<PedidoDetalleInput> detalles;

}
