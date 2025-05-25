package org.restaurante.msvc_erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @Column(name = "categoria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaId;

    private String nombre;
    private String descripcion;
    private Boolean estado;

    @Column(name="tenant_id")
    private String tenantId;

    @Column(name="created_at")
    private LocalDate createdAt;
}
