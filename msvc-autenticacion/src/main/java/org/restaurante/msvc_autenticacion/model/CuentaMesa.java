package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cuenta_mesa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMesa {

    @Override
    public int hashCode() {
        return Objects.hash(cuentaMesaId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "cuenta_mesa_seq")
    @SequenceGenerator(
            name = "cuenta_mesa_seq",
            sequenceName = "cuenta_mesa_seq",
            allocationSize = 1
    )
    @Column(name = "cuenta_mesa_id")
    private Long cuentaMesaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_hora_ini")
    private LocalDateTime fechaHoraIni;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @Column(name = "num_comensales")
    private Integer numComensales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (fechaHoraIni == null) {
            fechaHoraIni = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "ACTIVA";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
