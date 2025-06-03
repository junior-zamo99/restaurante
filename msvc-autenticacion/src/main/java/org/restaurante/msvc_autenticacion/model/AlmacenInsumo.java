package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "almacen_insumo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenInsumo {

    @Override
    public int hashCode() {
        return AlmacenInsumoId != null ? AlmacenInsumoId.hashCode() : 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "almaceninsumo_id")
    private Long AlmacenInsumoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "almacen_id")
    private Almacen almacen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    private Double cantidad;




}
