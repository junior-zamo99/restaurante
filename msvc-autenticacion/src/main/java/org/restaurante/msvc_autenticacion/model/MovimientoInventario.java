package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimiento_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    @Override
    public int hashCode(){
        return (int) (movimientoId != null ? movimientoId : 0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_inventario_seq")
    @SequenceGenerator(
            name = "movimiento_inventario_seq",
            sequenceName = "movimiento_inventario_seq",
            allocationSize = 1
    )
    private Long movimientoId;
    private Double cantidad;
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "almacen_insumo_id")
    private AlmacenInsumo almacenInsumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_movimiento_id")
    private TipoMovimiento tipoMovimiento;

}
