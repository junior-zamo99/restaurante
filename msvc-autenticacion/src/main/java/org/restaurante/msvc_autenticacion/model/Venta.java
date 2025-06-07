package org.restaurante.msvc_autenticacion.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Override
    public int hashCode() {
        return Objects.hash(ventaId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_seq")
    @SequenceGenerator(
            name = "venta_seq",
            sequenceName = "venta_seq",
            allocationSize = 1
    )
    @Column(name = "venta_id")
    private Long ventaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_mesa_id")
    private CuentaMesa cuentaMesa;


    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;


    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (fechaVenta == null) {
            fechaVenta = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "PENDIENTE";
        }

    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
