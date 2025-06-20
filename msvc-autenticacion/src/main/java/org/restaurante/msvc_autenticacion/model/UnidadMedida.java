package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "unidad_medida")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadMedida {

    @Override
    public int hashCode(){
        return Objects.hash(unidadMedidaId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidad_medida_seq")
    @SequenceGenerator(
            name = "unidad_medida_seq",
            sequenceName = "unidad_medida_seq",
            allocationSize = 1
    )
    @Column(name = "unidadmedida_id")
    private Long unidadMedidaId;

    private String nombre;
    private String abreviatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
