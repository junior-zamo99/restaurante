package org.restaurante.msvc_autenticacion.dto.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteSimpleDTO {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String username;
    private String telefono;
    private String email;
}