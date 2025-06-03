package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMovimiento {

    @Override
    public int hashCode() {
        return tipoMovimientoId != null ? tipoMovimientoId.hashCode() : 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_movimiento_id")
    private Long tipoMovimientoId;
    private String nombre;
    private String descripcion;
    @Column(name = "afecta_inventario")
    private boolean afectaInventario;



}
