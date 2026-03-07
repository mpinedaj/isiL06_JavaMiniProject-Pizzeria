package model;
import services.IdGenerator;

public class Pedido {
      private int id;
      private boolean activo;
      private Cliente cliente;
      private Producto producto;
      private DetallePedido detalle;
      private TipoEntrega tipoEntrega;
      
      private static class DetallePedido {
            private int cantidad;
            private String fecha;

            public DetallePedido(int cantidad, String fecha) {
                  this.cantidad = cantidad;
                  this.fecha = fecha;
            }

            public int getCantidad() { return this.cantidad; }
            public String getFecha() { return this.fecha; }
      }

      public Pedido(Cliente cliente, Producto producto, int cantidad, TipoEntrega tipoEntrega) {
            this.id = IdGenerator.nextId();
            this.activo = true;
            this.cliente = cliente;
            this.producto = producto;
            this.detalle = new DetallePedido(cantidad, "07/03/2026");
            this.tipoEntrega = tipoEntrega;
      }

      public int getId() { return this.id; }
      public Cliente getCliente() { return this.cliente; }
      public Producto getProducto() { return this.producto; }
      public int getCantidad() { return detalle.getCantidad(); }
      public String getFecha() { return detalle.getFecha(); }
      public TipoEntrega getTipoEntrega() { return this.tipoEntrega; }
      public boolean isActivo() { return this.activo; }

      public void cerrar() { this.activo = false; }
}
