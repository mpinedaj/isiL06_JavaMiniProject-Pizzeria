package ui;

import java.util.Scanner;
import model.Cliente;

public class Main {

    public static void main(String[] args) {

    Cliente[] clientes = new Cliente[5];

    clientes[0] = new Cliente("Juan", 20);
    clientes[1] = new Cliente("Maria", 22);

        Scanner sc = new Scanner(System.in);
        int op;

        do {
            System.out.println("\nNAVARROS PIZZERIA");
            System.out.println("1) Mostrar Menu");
            System.out.println("2) Listar Clientes");
            System.out.println("3) Realizar Pedido");
            System.out.println("4) Cancelar Pedido");
            System.out.println("5) Listar Pedidos");
            System.out.println("0) Salir");
            System.out.print("Opción: ");

            op = leerEntero(sc);
            System.out.println("El numero escogido fue " + op);

            switch (op) {

                case 1:
                    // Lógica para mostrar el menú de pizzas disponibles
                    // Aquí deberías imprimir todas las pizzas y sus precios
                    break;

                case 2:
                    // Lógica para listar los Clientes registrados
                    // Aquí deberías recorrer la lista de Clientes y mostrarlos
                    break;

                case 3:
                    // Lógica para realizar un pedido
                    // Aquí deberías:
                    // 1. Pedir el Cliente
                    // 2. Seleccionar pizza
                    // 3. Guardar el pedido
                    break;

                case 4:
                    // Lógica para cancelar un pedido
                    // Aquí deberías:
                    // 1. Pedir el ID del pedido
                    // 2. Buscar el pedido
                    // 3. Eliminarlo o marcarlo como cancelado
                    break;

                case 5:
                    // Lógica para listar todos los pedidos realizados
                    // Aquí deberías recorrer la lista de pedidos y mostrarlos
                    break;

                case 0:
                    // Lógica de salida del programa
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    // Opción inválida
                    System.out.println("Opción no válida");
            }

        } while (op != 0);

        sc.close();
    }

    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Ingrese un número: ");
        }
        return sc.nextInt();
    }
}