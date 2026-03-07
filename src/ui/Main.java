package ui;

import java.util.Scanner;
import model.*;
import services.*;

public class Main {

    public static void main(String[] args) {

    

    Cliente[] clientes = new Cliente[5]; //Declaro objetos Cliente

    clientes[0] = new Cliente("Juan", "20");
    clientes[1] = new Cliente("Martin", "22");
    clientes[2] = new Cliente("Arturo", "50");
    clientes[3] = new Cliente("Maria", "77");
    clientes[4] = new Cliente("Paula", "58");

    Producto[] productos = new Producto[11]; //Declaro objetos Producto

    productos[0] = new Pizza("Placer de antaño","M","Piña - Peperoni");
    productos[1] = new Pizza("Camaronzon","G","Camarones - Salsa");
    productos[2] = new Pizza("Carnívora Suprema","G","Carne molida - Chorizo - Peperoni");
    productos[3] = new Pizza("Veggie Deluxe","P","Champiñones - Pimentón - Cebolla - Maíz");
    productos[4] = new Pizza("Mexicana Picante","M","Carne - Jalapeños - Nachos triturados");
    productos[5] = new Pizza("Cuatro Quesos","P","Mozzarella - Cheddar - Parmesano - Azul");

    productos[6] = new Bebida("Cocacola", 350);
    productos[7] = new Bebida("Pepsi", 350);
    productos[8] = new Bebida("Sprite", 350);
    productos[9] = new Bebida("Jugo de Naranja", 400);
    productos[10] = new Bebida("Agua Mineral", 300);




    Pizzeria Atlas = new Pizzeria("Papas Pizzeria"); //Creamos la Pizzeria

    //Alimentamos de datos a la pizzeria Atlas
  
    for(int i=0; i< productos.length ;i++){ // Registramos los productos, un array no puede ingresar por constructor
        Atlas.registrarProducto(productos[i]);
    }

    for(int i=0; i< clientes.length ;i++){ // Registramos los clientes, un array no puede ingresar por constructor
        Atlas.registrarCliente(clientes[i]);
    }



        Scanner sc = new Scanner(System.in);
        int op;
        int idCliente;
        int idProducto;
        int cantidad;
        int tipoPedido;
        int idPedido;


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
                    System.out.println("Tenemos las siguientes Pizzas");
                    Atlas.mostrarMenu();
                    break;

                case 2:
                    System.out.println("Tenemos las siguientes Clientes");
                    Atlas.mostrarClientes();
                    break;

                case 3:
                    System.out.println("Que Cliente eres (Ingresa el ID)");
                    Atlas.mostrarClientes();
                    idCliente= leerEntero(sc);
                    

                    System.out.println("Que Producto deseas (Ingresa el ID)");
                    Atlas.mostrarMenu();
                    idProducto= leerEntero(sc);

                    System.out.println("Que cantidad va a llevar compadre (Ingrese cant)");
                    cantidad = leerEntero(sc);

                    System.out.println("Como lo va a llevar Domicilio [1], Para llevar [2], o Para aqui [3]");
                    tipoPedido = leerEntero(sc);
                    switch (tipoPedido) {
                        case 1:
                            Atlas.realizarPedido(idCliente,idProducto,cantidad,TipoEntrega.DOMICILIO);
                            break;
                        case 2:
                            Atlas.realizarPedido(idCliente,idProducto,cantidad,TipoEntrega.DOMICILIO);
                            break;
                        case 3:
                            Atlas.realizarPedido(idCliente,idProducto,cantidad,TipoEntrega.DOMICILIO);
                            break;
                        default:
                            break;
                    }


                    break;

                case 4:
                    System.out.println("Que pedido vas a cancelar [Ingrese id]");
                    Atlas.mostrarPedidos();
                    idPedido= leerEntero(sc);
                    Atlas.cancelarPedido(idPedido);
                    break;

                case 5:
                    System.out.println("Mostrando los pedidos");
                    Atlas.mostrarPedidos();
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
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