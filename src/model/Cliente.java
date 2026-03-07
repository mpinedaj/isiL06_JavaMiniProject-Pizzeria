package model;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;

    public Cliente(String nombre, String telefono) {
        this.id = 100; //Por ahora, mientras se implementa el generador de IDs
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getId() { return this.id; }
    public String getNombre() { return this.nombre; }
    public String getTelefono() { return this.telefono; }
}
