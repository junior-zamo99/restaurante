package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Rol  {
    @Override
    public int hashCode() {
        return Objects.hash(rolId); // Solo usar rolId
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "rol_id")
    private Long rolId;
    private String nombre;
    private String descripcion;
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rol_permiso",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private Set<Permiso> permisos = new HashSet<>();
   
    
}
