package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosVentaDTO {

    private Long ventaId;
    private Long tenantId;
    private String fechaVenta;
    private Double total;
    private String estado;
    private Long cuentaMesaId;
    private MesaBlockchainDTO mesa;
    private ClienteBlockchainDTO cliente;
    private List<ProductoBlockchainDTO> productos;

}
