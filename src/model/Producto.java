package model;
import service.IdGenerator;

public abstract class Producto implements Pedible {
    private int id;
    private String nombre;
    protected boolean disponible = true;

    protected Producto(String nombre) {
        this.id = IdGenerator.nextId(); 
        this.nombre = nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return;
        this.nombre = nombre; 
    }
    public int getId() { return this.id; }
    public String getNombre() { return this.nombre; }
    
    public abstract String getTipo();
    public abstract double calcularValorBase();

    @Override
    public void pedir() {
        System.out.println("Pedido");
        this.disponible = false;
    }

    @Override
    public void cancelar() {
        System.out.println("Cancelado");
        this.disponible = true;
    }

    @Override
    public boolean estaDisponible() { return disponible; }
    
    @Override
    public String toString() {
        return "[" + getTipo() + "] ID = " + id
                + ", Nombre = '" + nombre + "'"
                + ", Disponible = " + disponible;
    }
}

