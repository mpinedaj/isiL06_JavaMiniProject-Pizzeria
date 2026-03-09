package model;
import exceptions.PizzeriaException;
import services.IdGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido implements Entregable {
      private final int id;
      private EstadoPedido estado;
      private final Cliente cliente;
      private final Producto producto;
      private final DetallePedido detalle;
      private final TipoEntrega tipoEntrega;
      
      private static class DetallePedido {
            private final int cantidad;
            private final String fecha;

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
            this.estado = EstadoPedido.PENDIENTE;
            this.cliente = cliente;
            this.producto = producto;
            this.detalle = new DetallePedido(cantidad);
            this.tipoEntrega = tipoEntrega;
      }

      public int getId() { return this.id; }
      public Cliente getCliente() { return this.cliente; }
      public Producto getProducto() { return this.producto; }
      public int getCantidad() { return this.detalle.getCantidad(); }
      public String getFecha() { return this.detalle.getFecha(); }
      public TipoEntrega getTipoEntrega() { return this.tipoEntrega; }
      public EstadoPedido getEstado() { return this.estado; }
      @Override
      public void pedir()    { this.estado = EstadoPedido.EN_PROCESO; }

      @Override
      public boolean entregar() {
            if (this.estado == EstadoPedido.CANCELADO) {
                  throw new PizzeriaException("ERROR: No se puede entregar un pedido cancelado.");
            }
            this.estado = EstadoPedido.ENTREGADO;
            return true;
      }

      @Override
      public boolean cancelar() {
            if (this.estado == EstadoPedido.ENTREGADO) {
                  throw new PizzeriaException("ERROR: No se puede cancelar un pedido que ya ha sido entregado.");
            }
            this.estado = EstadoPedido.CANCELADO;
            return true;
      }

      @Override
      public boolean estaActivo() {
            return this.estado == EstadoPedido.EN_PROCESO || this.estado == EstadoPedido.PENDIENTE;
      }

      @Override
      public String toString() {
        return "Pedido ID = " + id + " | Cliente = "  + cliente.getNombre()+ " | Producto = " + producto.getNombre()+ " | Cantidad = " + detalle.getCantidad()+ " | Entrega = "  + tipoEntrega.getDescripcion()
                + " | Estado del pedido = "   + this.estado.getDescripcion();
     }
}
