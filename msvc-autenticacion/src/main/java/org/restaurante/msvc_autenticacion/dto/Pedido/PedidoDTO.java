package org.restaurante.msvc_autenticacion.dto.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.PedidoDetalle.PedidoDetalleSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long pedidoId;
    private LocalDateTime fechaHora;
    private String notas;
    private Double total;
    private Boolean estado;
    private TenantSimpleDTO tenant;
    private List<PedidoDetalleSimpleDTO> detalles;
    private String createdAt;

}
