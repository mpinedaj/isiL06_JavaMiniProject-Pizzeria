package model;

public abstract class Producto implements Pedible {
    private int id;
    private String nombre;
    protected boolean disponibilidad;

    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getId() { return this.id; }
    public String getNombre() { return this.nombre; }

    @Override
    public void pedir() {
        System.out.println("Pedido");
        this.disponibilidad = false;
    }

    @Override
    public void cancelar() {
        System.out.println("Cancelado");
        this.disponibilidad = true;
    }

    @Override
    public boolean estaDisponible() { return disponibilidad; }
}
