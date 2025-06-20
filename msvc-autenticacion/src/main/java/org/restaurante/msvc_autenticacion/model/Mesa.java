package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mesa {

    @Override
    public int hashCode() {
        return (int) (mesaId != null ? mesaId : 0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mesa_seq")
    @SequenceGenerator(
            name = "mesa_seq",
            sequenceName = "mesa_seq",
            allocationSize = 1
    )
    private Long mesaId;
    private Integer numero;
    private Integer capacidad;
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;






}
