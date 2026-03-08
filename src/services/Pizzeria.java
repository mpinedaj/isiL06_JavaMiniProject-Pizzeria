package services;

import java.util.ArrayList;

import exceptions.PizzeriaException;
import model.*;
import services.Reglas;

public class Pizzeria {
    private final String nombre;

    private final ArrayList<Producto> menu = new ArrayList<>();
    private final ArrayList<Cliente>  clientes = new ArrayList<>();
    private final ArrayList<Pedido>   pedidos  = new ArrayList<>();
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
}
