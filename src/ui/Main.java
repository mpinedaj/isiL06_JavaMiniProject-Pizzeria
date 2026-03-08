package ui;

import java.util.Scanner;
import model.*;
import services.*;
import exceptions.PizzeriaException;
public class Main {
    
    public static void main(String[] args) throws InterruptedException {

        PizzaAnimation.mostrar();

        Pizzeria atlas = new Pizzeria("Papa's Pizzeria");
        cargaInicialDeDatos(atlas);

        Scanner sc = new Scanner(System.in);
        int op;
        do {
            System.out.println("\n── " + atlas.getNombre() + " ──");
            menuPrincipal();
            op = leerEntero(sc);

            try {
                switch (op) {
                    case 1:
                        atlas.mostrarMenu();
                        break;
                    case 2:
                        atlas.mostrarClientes();
                        break;
                    case 3:
                        System.out.println("\n--- NUEVO PEDIDO ---");
                        atlas.mostrarClientes();
                        System.out.print("ID Cliente: ");
                        int idCliente = leerEntero(sc);
                        atlas.mostrarMenu();
                        System.out.print("ID Producto: ");
                        int idProducto = leerEntero(sc);
                        System.out.print("Cantidad: ");
                        int cantidad = leerEntero(sc);
                        TipoEntrega tipo = elegirTipoEntrega(sc);
                        atlas.realizarPedido(idCliente, idProducto, cantidad, tipo);
                        break;
                    case 4:
                        if (atlas.mostrarPedidosActivos()) {
                            System.out.print("ID pedido a entregar: ");
                            int idEntregar = leerEntero(sc);
                            atlas.entregarPedido(idEntregar);
                        }
                        break;
                    case 5:
                        if (atlas.mostrarPedidosActivos()) {
                            System.out.print("ID Pedido a cancelar: ");
                            int idCancelar = leerEntero(sc);
                            atlas.cancelarPedido(idCancelar);
                        }
                        break;
                    case 6:
                        atlas.mostrarPedidos();
                        break;
                    case 7:
                        System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");
                        sc.nextLine(); // limpiar el buffer
                        System.out.print("Nombre completo: ");
                        String nombre = sc.nextLine();
                        System.out.print("Teléfono (Opcional, 10 dígitos): ");
                        String telefono = sc.nextLine();

                        try {
                            Cliente nuevo = new Cliente(nombre, telefono);
                            atlas.registrarCliente(nuevo);
                            System.out.println("Cliente registrado exitosamente.");
                        } catch (PizzeriaException e) {
                            System.out.println("\n Error al registrar: " + e.getMessage());
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (PizzeriaException e) {
                System.err.println("\nERROR: " + e.getMessage());
            }

        } while (op != 0);
        sc.close();
    }

    private static void menuPrincipal() {
        System.out.println("1) Mostrar Menú");
        System.out.println("2) Listar Clientes");
        System.out.println("3) Realizar Pedido");
        System.out.println("4) Entregar Pedido");
        System.out.println("5) Cancelar Pedido");
        System.out.println("6) Listar Pedidos");
        System.out.println("7) Registrar Cliente");
        System.out.println("0) Salir");
        System.out.print("Opción: ");
    }

    private static void cargaInicialDeDatos(Pizzeria pizz) {
        pizz.registrarCliente(new Cliente("Juan Diaz", "3132300200"));
        pizz.registrarCliente(new Cliente("Martin Marco", "3132300222"));
        pizz.registrarCliente(new Cliente("Arturo Arias", "3132302122"));
        pizz.registrarCliente(new Cliente("Maria Mar", "3132303344"));

        pizz.registrarProducto(new Pizza("Placer de antano", "M", "Pina - Peperoni"));
        pizz.registrarProducto(new Pizza("Camaronzon", "G", "Camarones - Salsa"));
        pizz.registrarProducto(new Pizza("Carnivora Suprema", "G", "Carne molida - Chorizo - Peperoni"));
        pizz.registrarProducto(new Pizza("Veggie Deluxe", "P", "Champinones - Pimenton - Cebolla - Maiz"));
        pizz.registrarProducto(new Pizza("Mexicana Picante", "M", "Carne - Jalapenos - Nachos triturados"));
        pizz.registrarProducto(new Bebida("Cocacola", 350));
        pizz.registrarProducto(new Bebida("Sprite", 350));
        pizz.registrarProducto(new Bebida("Agua", 300));
        System.out.println("Datos cargados");
    }

    private static TipoEntrega elegirTipoEntrega(Scanner sc) {
        System.out.println("Tipo de entrega:");
        System.out.println(" 1) " + TipoEntrega.PARA_AQUI);
        System.out.println(" 2) " + TipoEntrega.PARA_LLEVAR);
        System.out.println(" 3) " + TipoEntrega.DOMICILIO);
        System.out.print("Opcion: ");
    
        int opcion = leerEntero(sc); 
    
        switch (opcion) {
            case 1:
                return TipoEntrega.PARA_AQUI;
            case 2:
                return TipoEntrega.PARA_LLEVAR;
            case 3:
                return TipoEntrega.DOMICILIO;
            default:
                System.out.println("Opción no válida, asignando para comer aquí.");
                return TipoEntrega.PARA_AQUI;
        }
    }

    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Ingrese un número: ");
        }
        return sc.nextInt();
    }
}
