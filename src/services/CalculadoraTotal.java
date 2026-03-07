package services;

import model.TipoEntrega;

public class CalculadoraTotal {

    public double calcular(double valorBase, int cantidad, TipoEntrega tipoEntrega) {
        double subtotal = valorBase * cantidad;

        if (cantidad >= Reglas.CANT_DESCUENTO) {
            subtotal = subtotal * 0.80;
        }

        double costoEntrega = tipoEntrega.getCostoAdicional();
        return subtotal + costoEntrega;
    }
}
