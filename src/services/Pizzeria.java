package services;

import java.util.ArrayList;

import exceptions.PizzeriaException;
import model.*;
import services.Reglas;

public class Pizzeria {
    private final String nombre;
    private final ArrayList<Producto> menu = new ArrayList<>();
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Pedido> pedidos  = new ArrayList<>();
    private final ArrayList<Reserva> reservas  = new ArrayList<>();
    private final CalculadoraTotal calculadoraTotal = new CalculadoraTotal();

    public Pizzeria(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }

    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n---- Clientes ----");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    public void mostrarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }
        System.out.println("\n--- Pedidos ---");
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    public void mostrarMenu() {
        if (menu.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("\n--- Menú ---");
        for (Producto p : menu) {
          System.out.println("  " + p + " | Precio base: $" + (int)p.calcularValorBase());
        }
    }

    public void registrarProducto(Producto p) {
        if (p == null) return;
        menu.add(p);
    }

    public void registrarCliente(Cliente c) {
        if (c == null) return;
        clientes.add(c);
    }

    public void realizarPedido(int idCliente, int idProducto, int cantidad, TipoEntrega tipoEntrega) {
        Cliente c = buscarCliente(idCliente);
        Producto p = buscarProducto(idProducto);

        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        if (p == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        if (!p.estaDisponible()) {
            throw new PizzeriaException("El producto '" + p.getNombre() + "' no está disponible actualmente.");
        }

        if (cantidad < 1 || cantidad > Reglas.MAX_CANT) {
            throw new PizzeriaException("Cantidad inválida. Debe pedir entre 1 y " + Reglas.MAX_CANT + " unidades.");
        }
        Pedido pedido = new Pedido(c, p, cantidad, tipoEntrega);
        pedido.pedir();
        pedidos.add(pedido);

        double valorBase = p.calcularValorBase();
        double total = calculadoraTotal.calcular(valorBase, cantidad, tipoEntrega);
        imprimirTicket(pedido, c, p, valorBase, total);
    }
    private void imprimirTicket(Pedido pedido, Cliente c, Producto p, double valorBase, double total) {
        System.out.println("\n========================================");
        System.out.println("           RESUMEN DEL PEDIDO           ");
        System.out.println("========================================");
        System.out.println(" ID Pedido  : " + pedido.getId());
        System.out.println(" Cliente    : " + c.getNombre());
        System.out.println(" Producto   : " + p.getNombre());
        System.out.println(" Cantidad   : " + pedido.getCantidad());
        System.out.println(" Entrega    : " + pedido.getTipoEntrega());
        System.out.println("----------------------------------------");
        System.out.printf( " Subtotal   : $%,.2f%n", (valorBase * pedido.getCantidad()));
        System.out.printf( " TOTAL      : $%,.2f%n", total);
        System.out.println("========================================\n");
    }
    public void entregarPedido(int idPedido) {
        Pedido p = buscarPedido(idPedido);
        if (p.entregar()) {
            System.out.println("Confirmación: Pedido " + idPedido + " marcado como entregado.");
        }
    }

    public void cancelarPedido(int idPedido) {
        Pedido p = buscarPedido(idPedido);
        if (p.cancelar()) {
            System.out.println("Confirmación: Pedido " + idPedido + " ha sido cancelado.");
        }
    }

    public boolean mostrarPedidosActivos() {
        System.out.println("\n--- Pedidos Activos ---");
        boolean hay = false;
        for (Pedido p : pedidos) {
            if (p.estaActivo()) {
                System.out.println(p);
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("No hay pedidos activos.");
        }
        return hay;
    }

    public void registrarReserva(int idCliente, int numPersonas, String hora) {
        if (reservas.size() == Reglas.MAX_CANT_RESERVAS) {
            throw new PizzeriaException("Máxima cantidad de reservas activas alcanzada. Confirme o cancele una reserva activa para poder registrar una nueva");
        }
        if (numPersonas < 1 || numPersonas > Reglas.MAX_CANT_PERSONAS_X_RESERVA) {
            throw new PizzeriaException("Número de personas inválida, solo puede registrar entre 1 y " + Reglas.MAX_CANT_PERSONAS_X_RESERVA + " en la reserva.");
        }
        Cliente c = buscarCliente(idCliente);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Reserva r = new Reserva(c, numPersonas, hora);
        r.pedir();
        reservas.add(r);
        System.out.println("Reserva creada: ID = " + r.getId() + " | Hora = " + hora);
    }

    public void confirmarReserva(int idReserva) {
        Reserva r = buscarReserva(idReserva);
        if (r == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }
        if (r.entregar()) {
            System.out.println("Reserva " + idReserva + " confirmada.");
        }
    }

    public void cancelarReserva(int idReserva) {
        Reserva r = buscarReserva(idReserva);
        if (r == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }
        if (r.cancelar()) {
            System.out.println("Reserva " + idReserva + " cancelada.");
        }
    }

    public void mostrarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas.");
            return;
        }
        System.out.println("\n--- Reservas ---");
        for (Reserva r : reservas) {
            System.out.println(r);
        }

    }

    public boolean mostrarReservasActivas() {
        System.out.println("\n--- Reservas Activas ---");
        boolean hay = false;
        for (Reserva r : reservas) {
            if (r.estaActivo()) {
                System.out.println(r);
                hay = true;
            }
        }
        if (!hay){
            System.out.println("No hay reservas activas.");
        }
        return hay;
    }

    private Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        throw new PizzeriaException("Cliente con ID " + id + " no encontrado.");
    }

    private Producto buscarProducto(int id) {
        for (Producto p : menu) {
            if (p.getId() == id) return p;
        }
        throw new PizzeriaException("Producto con ID " + id + " no encontrado en el menú.");
    }

    private Pedido buscarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) return p;
        }
        throw new PizzeriaException("Pedido con ID " + id + " no encontrado en el sistema.");
    }
    private Reserva buscarReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) return r;
        }
        throw new PizzeriaException("Reserva con ID " + id + " no encontrada en el sistema.");
    }
}
