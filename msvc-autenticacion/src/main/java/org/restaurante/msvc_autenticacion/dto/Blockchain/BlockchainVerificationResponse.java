package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockchainVerificationResponse {
    private boolean isValid;
    private String message;
    private Date timestamp;
    private DatabaseVerificationDTO databaseVerification;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DatabaseVerificationDTO {
        private String hashEnBD;
        private boolean coincideConBD;
        private Date fechaCreacion;
        private String estadoEnBD;
    }
}