package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@Table(name = "permiso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permiso {

    @Override
    public int hashCode() {
        return Objects.hash(permisoId); // Solo usar permisoId
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permiso_id")
    private Long permisoId;

    private String codigo;
    private String nombre;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

}
