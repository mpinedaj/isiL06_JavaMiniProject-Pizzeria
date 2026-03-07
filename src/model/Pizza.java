package model;

public class Pizza extends Producto {
     private String tamano;
     private String sabor;

     public Pizza(String nombre, String tamano, String sabor) {
         super(nombre);
         this.tamano = tamano;
         this.sabor = sabor;
     }

     public String getTamano() { return this.tamano; }
     public String getSabor() { return this.sabor; }

     @Override
     public double calcularValorBase() {
         switch (this.tamano.toLowerCase()) {
             case "pequena":
                 return 10000.0;
             case "mediana":
                 return 20000.0;
             case "grande":
                 return 40000.0;
             default:
                 System.out.println("ERROR, tamaño inválido.");
                 return 0;
         }
     
     }
     @Override
    public String getTipo() { return "Pizza"; }

    @Override
    public String toString() {
        return super.toString() + " (Sabor = " + sabor + ", Tamaño = " + tamano + ")";
    }
     //===== Cambiar despues de que se hagan las reglas ======//
}
