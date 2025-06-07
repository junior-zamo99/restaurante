package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteBlockchainDTO {

    private Long clienteId;
    private String nombre;
    private String email;

}
