package org.restaurante.msvc_autenticacion.dto.CuentaMesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurante.msvc_autenticacion.dto.Cliente.ClienteSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.Mesa.MesaSimpleDTO;
import org.restaurante.msvc_autenticacion.dto.TenantSimpleDTO;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMesaDTO {
    private Long cuentaMesaId;
    private MesaSimpleDTO mesa;
    private String estado;
    private String fechaHoraIni;
    private String fechaHoraFin;
    private Integer numComensales;
    private ClienteSimpleDTO cliente;
    private BigDecimal montoTotal;
    private TenantSimpleDTO tenant;
    private String createdAt;
}