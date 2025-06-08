package org.restaurante.msvc_autenticacion.dto.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInput {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String username;
    private String password;
    private String direccion;
    private String fechaNacimiento;
    private Boolean estado;
    private Long tenantId;
}