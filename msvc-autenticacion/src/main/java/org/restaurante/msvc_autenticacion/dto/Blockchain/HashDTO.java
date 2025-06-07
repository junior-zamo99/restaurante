package org.restaurante.msvc_autenticacion.dto.Blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashDTO {

    private Long id;
    private Long ventaId;
    private Long tenantId;
    private String hash;
    private Date createdAt;
    private String status;


}
