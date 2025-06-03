package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "tenant")
@NoArgsConstructor
@AllArgsConstructor
public class Tenant  {

    @Override
    public int hashCode() {
        return Objects.hash(tenantId);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tenant tenant = (Tenant) obj;
        return Objects.equals(tenantId, tenant.tenantId);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tenant_id")
    private Long tenantId;

    @NotBlank
    @Size(max = 100)
    private String ciudad;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Size(max = 200)
    @Column(name = "razon_social")
    private String razonSocial;

    @Size(max = 50)
    private String nit;

    @NotBlank
    @Size(max = 20)
    private String estado;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "fecha_activacion")
    private LocalDate fechaActivacion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Size(max = 100)
    @Column(name = "contacto_nombre")
    private String contactoNombre;

    @Size(max = 255)
    private String direccion;

    @Size(max = 100)
    @Column(name = "contacto_email")
    private String contactoEmail;

    @Size(max = 20)
    @Column(name = "contacto_telefono")
    private String contactoTelefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suscripcion_id")
    private Suscripcion suscripcion;


    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private Set<Rol> roles = new HashSet<>();

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private Set<Permiso> permisos = new HashSet<>();


}
