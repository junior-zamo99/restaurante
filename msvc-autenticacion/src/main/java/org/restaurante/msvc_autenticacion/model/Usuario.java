package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario  {

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "usuario_id")
    private Long usuarioId;

    private String nombre;

    private String apellido;

    @Email
    private String email;

    private String password;

    private String telefono;

    private String username;

    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

     @ManyToMany(fetch = FetchType.LAZY)
     @JoinTable(name = "usuario_rol",
             joinColumns = @JoinColumn(name = "usuario_id"),
             inverseJoinColumns = @JoinColumn(name = "rol_id"))
     private Set<Rol> roles = new HashSet<>();

    public Set<Rol> getRoles() {
        return roles;
    }
}
