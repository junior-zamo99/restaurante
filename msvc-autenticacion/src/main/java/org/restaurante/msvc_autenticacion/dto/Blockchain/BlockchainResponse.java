package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockchainResponse {
    private boolean success;
    private String message;
    private String error;
    private HashDTO hash;
    private Long databaseId;

}
