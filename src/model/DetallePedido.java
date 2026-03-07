package model;

public class DetallePedido {
    private int cantidad;
    private String fecha;

    public DetallePedido(int cantidad, String fecha) {
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getCantidad() { return this.cantidad; }
    public String getFecha() { return this.fecha; }
}
