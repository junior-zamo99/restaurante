package org.restaurante.msvc_autenticacion.dto.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String telefono;
    private String direccion;
    private String fechaNacimiento;
    private Boolean estado;
    private TenantSimpleDTO tenant;
    private String createdAt;
}