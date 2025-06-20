package org.restaurante.msvc_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Override
    public int hashCode() {
        return Objects.hash(reservaId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_seq")
    @SequenceGenerator(
            name = "reserva_seq",
            sequenceName = "reserva_seq",
            allocationSize = 1
    )
    @Column(name = "reserva_id")
    private Long reservaId;

    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "cantidad_personas")
    private Integer cantidadPersonas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = true)
    private Boolean mensajeConfirmacionEnviado;

    @Column(nullable = true)
    private LocalDateTime mensajeConfirmacionEnviadoEn;

    @Column(nullable = true)
    private LocalDateTime horaLimiteConfirmacion;

    @Column(nullable = true)
    private Boolean confirmada;

    @Column(name = "estado")
    private String estado;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

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
        if (estado == null) {
            estado = "Activa";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
