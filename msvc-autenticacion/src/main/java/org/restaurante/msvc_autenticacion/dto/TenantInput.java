package org.restaurante.msvc_autenticacion.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantInput {

    private String ciudad;
    private String nombre;
    private String razonSocial;
    private String nit;
    private String estado;
    private LocalDate fechaRegistro;
    private LocalDate fechaActivacion;
    private LocalDate fechaVencimiento;
    private String contactoNombre;
    private String direccion;
    private String contactoEmail;
    private String contactoTelefono;
    private Long suscripcionId;

}
