package org.restaurante.msvc_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDTO {

    private Long tenantId;
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
    private SuscripcionDTO suscripcion;
    private List<UsuarioDTO> usuarios;
    private List<RolDTO> roles;
    private List<PermisoDTO> permisos;
    private String createdAt;

}
