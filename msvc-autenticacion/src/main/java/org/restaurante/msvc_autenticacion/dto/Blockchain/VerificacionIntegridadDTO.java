package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class VerificacionIntegridadDTO {
    private Long ventaId;
    private boolean integra;
    private Date fechaVerificacion;
    private String mensaje;
}