package model;

public enum EstadoPedido {
    PENDIENTE (true, "Pendiente de preparación"),
    EN_PROCESO(true, "En preparación"),
    ENTREGADO (false, "Entregado"),
    CANCELADO (false, "Cancelado");

    private final boolean activo;
    private final String descripcion;

    EstadoPedido(boolean activo, String descripcion) {
        this.activo = activo;
        this.descripcion = descripcion;
    }

    public boolean getEstadoActividad() { return this.activo; }
    public String getDescripcion() { return this.descripcion; }

    @Override
    public String toString() {
        return this.descripcion + ((this.activo) ? " -> pedido activo." : " -> pedido inactivo.");
    }
}
