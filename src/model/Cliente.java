package model;
import services.IdGenerator;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;

    public Cliente(String nombre, String telefono) {
        this.id = IdGenerator.nextId();
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getId() { return this.id; }
    public String getNombre() { return this.nombre; }
    public String getTelefono() { return this.telefono; }
    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) return;
        this.telefono = telefono;
    }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Cliente ID = " + id + ", Nombre ='" + nombre + "'" + ", Tel = " + telefono;
    }
}
