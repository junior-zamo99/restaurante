package org.restaurante.msvc_autenticacion.dto.Venta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.CuentaMesa.CuentaMesaSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    private Long ventaId;
    private TenantSimpleDTO tenant;
    private BigDecimal total;
    private CuentaMesaSimpleDTO cuentaMesa;
    private String estado;
    private String fechaVenta;
    private String observaciones;
    private String createdAt;
}