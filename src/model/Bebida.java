package model;

public class Bebida extends Producto {
    private int volumenMl;

    public Bebida(String nombre, int volumenMl) {
        super(nombre);
        this.volumenMl = volumenMl;
    }

    public int getVolumenMl() { return this.volumenMl; }
    
    @Override
    public String getTipo() { return "Bebida"; }

    @Override
    public double calcularValorBase() {
        if (this.volumenMl <= 300) return 3000;
        if (this.volumenMl <= 500) return 4500;
        return 6000;
    }
    @Override
    public String toString() {
        return super.toString() + " (Volumen = " + this.volumenMl + "ml)";
    }
}
