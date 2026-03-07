package model;
import services.IdGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

            public DetallePedido(int cantidad) {
                  this.cantidad = (cantidad > 0) ? cantidad : 1;
                  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                  this.fecha = LocalDateTime.now().format(dtf);
            }

            public int getCantidad() { return this.cantidad; }
            public String getFecha() { return this.fecha; }
      }

      public Pedido(Cliente cliente, Producto producto, int cantidad, TipoEntrega tipoEntrega) {
            this.id = IdGenerator.nextId();
            this.activo = true;
            this.cliente = cliente;
            this.producto = producto;
            this.detalle = new DetallePedido(cantidad);
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
      
      @Override
      public String toString() {
        return "Pedido ID = " + id + " | Cliente = "  + cliente.getNombre()+ " | Producto = " + producto.getNombre()+ " | Cantidad = " + detalle.getCantidad()+ " | Entrega = "  + tipoEntrega.getDescripcion()
                + " | Activo = "   + activo;
     }
}
