package org.restaurante.msvc_autenticacion.model;

public enum EstadoPedidoDetalle {
    PENDIENTE("Pendiente"),
    EN_COMANDA("En Comanda"),
    PREPARANDO("Preparando"),
    LISTO("Listo"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoPedidoDetalle(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
