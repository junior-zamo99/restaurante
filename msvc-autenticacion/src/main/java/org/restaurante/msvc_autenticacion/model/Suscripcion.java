package org.restaurante.msvc_autenticacion.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "suscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Override
    public int hashCode() {
        return Objects.hash(suscripcionId); // Solo usar suscripcionId
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suscripcion_id")
    private Long suscripcionId;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @PositiveOrZero
    private BigDecimal precio;

    @OneToMany(mappedBy = "suscripcion", cascade = CascadeType.ALL)
    private Set<Tenant> tenants = new HashSet<>();

}
