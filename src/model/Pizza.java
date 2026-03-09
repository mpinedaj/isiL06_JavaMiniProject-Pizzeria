package model;
import services.Reglas;

public class Pizza extends Producto {
     private final String tamano;
     private final String sabor;

     public Pizza(String nombre, String tamano, String sabor) {
         super(nombre);
         this.tamano = tamano;
         this.sabor = sabor;
     }

     public String getTamano() { return this.tamano; }
     public String getSabor() { return this.sabor; }

     @Override
     public double calcularValorBase() {
         return switch (this.tamano.toUpperCase()) {
             case "P", "PEQUENA"  -> Reglas.PRECIO_PIZZA_PEQUENA;
             case "M", "MEDIANA"  -> Reglas.PRECIO_PIZZA_MEDIANA;
             case "G", "GRANDE"   -> Reglas.PRECIO_PIZZA_GRANDE;
             default -> {
                 System.out.println("ERROR: tamaño inválido: " + this.tamano);
                 yield 0;
             }
         };
     }

     @Override
    public String getTipo() { return "Pizza"; }

    @Override
    public String toString() {
        return super.toString() + " (Sabor = " + this.sabor + ", Tamaño = " + this.tamano + ")";
    }

}
