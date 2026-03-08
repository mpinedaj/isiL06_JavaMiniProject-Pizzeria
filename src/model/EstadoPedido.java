package model;

public enum EstadoPedido {
    PENDIENTE (true, "Pedido en cocina"),
    EN_PROCESO(true, "Pedido en preparación"),
    ENTREGADO (false, "Pedido entregado"),
    CANCELADO (false, "Pedido cancelado");

    private final boolean activo;
    private final String descripcion;

    EstadoPedido(boolean activo, String descripcion) {
        this.activo = activo;
        this.descripcion = descripcion;
    }

    public boolean getEstadoActividad() { return this.activo; }
    public String getDescripcion() { return this.descripcion; }
}
