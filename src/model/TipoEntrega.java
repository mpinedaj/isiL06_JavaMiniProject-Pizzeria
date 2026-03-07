package model;

// Define los tipos de entrega disponibles en la pizzería y sus costos asociados.

public enum TipoEntrega {
    PARA_AQUI (0.0, "Para aquí"),
    PARA_LLEVAR (300.0, "Para llevar"),
    DOMICILIO (3000.0, "Envío a domicilio");

    private final double costoAdicional;
    private final String descripcion;

    TipoEntrega(double costoAdicional, String descripcion) {
        this.costoAdicional = costoAdicional;
        this.descripcion = descripcion;
    }

    public double getCostoAdicional() { return costoAdicional; }
    public String getDescripcion()    { return descripcion; }

    @Override
    public String toString() {
        return descripcion + (costoAdicional > 0 ? "(+$"+ (int)costoAdicional + ")" : "");
    }
}
