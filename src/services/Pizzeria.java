package services;

public class Pizzeria {
import java.util.ArrayList;
import model.Producto;
import model.Pedido;
import model.Cliente;
import model.TipoEntrega;
import util.Reglas;


    public class Pizzeria {
        private final String nombre;

        private final ArrayList<Producto> menu     = new ArrayList<>();
        private final ArrayList<Cliente>  clientes = new ArrayList<>();
        private final ArrayList<Pedido>   pedidos  = new ArrayList<>();

        private final CalculadoraTotal calculadoraTotal = new CalculadoraTotal();

        public Pizzeria(String nombre) { this.nombre = nombre; }

        public String getNombre() { return nombre; }

        // ── Mostrar ──────────────────────────────────────────────────────
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
                System.out.println(p);
            }
        }

        // ── Registrar ────────────────────────────────────────────────────
        public void registrarProducto(Producto p) {
            if (p == null) return;
            menu.add(p);
        }

        public void registrarCliente(Cliente c) {
            if (c == null) return;
            clientes.add(c);
        }

        // ── Realizar pedido ──────────────────────────────────────────────
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
                System.out.println("El producto no está disponible.");
                return;
            }
            if (cantidad > Reglas.MAX_CANT) {
                System.out.println("Cantidad excede el máximo permitido (" + Reglas.MAX_CANT + ").");
                return;
            }

            Pedido pedido = new Pedido(c, p, cantidad, tipoEntrega);
            pedidos.add(pedido);

            p.pedir();

            double valorBase = p.calcularValorBase();
            double total = calculadoraTotal.calcular(valorBase, cantidad, tipoEntrega);

            System.out.println("Pedido creado: ID=" + pedido.getId());
            System.out.println("Valor base = " + valorBase + " | Total = " + total);
        }

        // ── Cancelar pedido ──────────────────────────────────────────────
        public void cancelarPedido(int idPedido) {
            Pedido p = buscarPedido(idPedido);
            if (p == null) {
                System.out.println("Pedido no encontrado.");
                return;
            }
            if (!p.isActivo()) {
                System.out.println("Ese pedido ya fue cerrado.");
                return;
            }

            Producto prod = p.getProducto();                             // FIX: variable con nombre distinto
            prod.cancelar();                                             // interfaz Pedible
            p.cerrar();

            System.out.println("Pedido " + idPedido + " cancelado.");
        }

        // ── Búsquedas (private) ──────────────────────────────────────────
        private Cliente buscarCliente(int id) {
            for (Cliente c : clientes) {
                if (c.getId() == id) return c;
            }
            return null;
        }

        private Producto buscarProducto(int id) {
            for (Producto p : menu) {                                    // FIX: iterar sobre "menu"
                if (p.getId() == id) return p;
            }
            return null;
        }

        private Pedido buscarPedido(int id) {
            for (Pedido p : pedidos) {
                if (p.getId() == id) return p;
            }
            return null;
        }
    }
}
