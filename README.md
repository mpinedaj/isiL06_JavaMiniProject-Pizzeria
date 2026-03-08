# Sistema de Gestión de Pizzería - Mini Proyecto POO
> Mini gestor de pedidos de pizzería basado en Java, diseñado bajo arquitectura de capas (Model-Service-UI) y principios POO.


```
         .,:::,.,
      .:::::::::::::.
    .::  o   :::  o  ::.
   :::   :::::::::::   ::
   ::  o  ::: ~ :::  o ::
   :::    ::: ~ :::    ::
    '::  o  :::::  o  ::'
      ':::::::::::::'
         ':::::::'

        P A P A ' S  
        pizzeria artesanal
```

---
## Estructura del proyecto
```
src/
├── model/
│   ├── Entregable.java 
│   ├── Producto.java  
│   ├── Pizza.java 
│   ├── Bebida.java 
│   ├── Cliente.java
│   ├── TipoEntrega.java   
│   └── Pedido.java   
├── service/
│   ├── IdGenerator.java   
│   ├── Reglas.java
│   ├── CalculadoraTotal.java
│   └── Pizzeria.java
└── ui/
    └── Main.java
```
---
## A. Relaciones entre clases

### 1. Uso (Dependency)
`CalculadoraTotal` usa a `TipoEntrega` para calcular el total con base a su información, porque la recibe como parámetro.
`Pizzeria` usa `CalculadoraTotal` invocando su método estático `calcular()`:

```java
// Pizzeria.java
 double total = calculadoraTotal.calcular(valorBase, cantidad, tipoEntrega);
```

### 2. Asociación
`Pedido` referencia a `Cliente` y a `TipoEntrega` , objetos que existen independientemente del pedido pero que se relaciona con él. 

```java
// Pedido.java
private Cliente cliente;   
```

### 3. Agregación
`Pizzeria` agrega listas de `Producto`, `Cliente` y `Pedido`.
Los objetos se crean fuera de `Pizzeria` (específicamente en `Main.java`) y se pasan como
parámetros, por lo que tienen existen por su cuenta.

```java
// Main.java
pizz.registrarCliente(new Cliente("Juan Diaz", "3132300200"));
pizz.registrarProducto(new Pizza("Placer de antano", "M", "Pina - Peperoni"));
```


### 4. Composición
`Pedido` crea internamente `DetallePedido`; este objeto no tiene sentido ni existencia fuera del pedido que lo contiene (clase estática privada).

```java
// Pedido.java
private static class DetallePedido {
    private int cantidad;
    DetallePedido(int cantidad) { ... }
}
this.detalle = new DetallePedido(cantidad); // se crea adentro de Pedido ---> composición
```

---

## B. Visibilidad y control de acceso

  | Miembro | Modificador | Justificación |
  | :---:       |     :---:      |          :---: |
  | `Producto.id`, `nombre` | `private` | Solo accesible con getters/setters |
  | `Producto.disponible` | `protected` | Las subclases `Pizza`/`Bebida` pueden leerlo |
  | `IdGenerator.current` | `private static` | Estado interno del generador |
  | `Reglas.*` | `public static final` | Constantes globales del sistema |

Validaciones empleadas en setters:
```java
public void setNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) return; 
    this.nombre = nombre;
}
```

---

## C. Herencia

```
Producto (abstracta)
  - Pizza    
  - Bebida   
```

---

## D. Polimorfismo

`Pizzeria.mostrarMenu()` recorre `ArrayList<Producto>` y llama `calcularPrecioBase()` en cada objeto; Java ejecuta la versión correcta
según el tipo real de producto (`Pizza` o `Bebida`).

```java
System.out.println("\n--- Menú ---");
for (Producto p : menu) {
   System.out.println("  " + p + " | Precio base: $" + (int)p.calcularValorBase());
}
```

---


## E. Clase abstracta

`Producto` es abstracta y obliga a sus subclases a implementar:
- `getTipo()`: devuelve el tipo de producto.
- `calcularPrecioBase()`: cada producto define su propio precio.

---

## F. Interfaz

```java
public interface Entregable {
    public void pedir();
    public boolean cancelar();
    public boolean estaActivo ();
    public boolean entregar();
}

```

`Pedido` implements `Entregable`. Se utiliza la interfaz Entregable para definir el comportamiento que debe tener cualquier objeto que pueda ser entregado o cancelado. Por ejemplo, paara una futura implementación
de una clase `Reserva`, la cual tambien puede ser pedida, cancelada, entregada o validar su estado. 
---

## G. Modificador `static`

| Uso | Archivo | Descripción |
|:---:|:---:|:---:|
| `IdGenerator.current` | `IdGenerator.java` | Contador estático compartido |
| `IdGenerator.nextId()` | `IdGenerator.java` | Método estático generador de IDs |
| `Reglas.PRECIO_PIZZA_*` | `Reglas.java` | Constantes `static final` |
| `CalculadoraTotal.calcular()` | `CalculadoraTotal.java` | Método utilitario estático |
| `DetallePedido` (clase interna) | `Pedido.java` | Clase interna estática privada |
| `TipoEntrega.*` | `TipoEntrega.java` | Valores enum son static final implícitos (colecciones de constantes fijas) |
| `EstadoPedido.*` | `EstadoPedido.java` | Valores enum son static final implícitos (colecciones de constantes fijas) |
---


## Casos de prueba manuales

### Caso 1: Listar menú (polimorfismo)
**Acción:** opción `1`

**Resultado esperado:** muestra Pizza y Bebida con sus precios base distintos.

**Ejemplo:**
```
--- Menú ---
  [Pizza] ID = 5, Nombre = 'Placer de antano', Disponible = true (Sabor = Pina - Peperoni, Tamaño = M) | Precio base: $20000
  [Pizza] ID = 6, Nombre = 'Camaronzon', Disponible = true (Sabor = Camarones - Salsa, Tamaño = G) | Precio base: $35000
  [Pizza] ID = 7, Nombre = 'Carnivora Suprema', Disponible = true (Sabor = Carne molida - Chorizo - Peperoni, Tamaño = G) | Precio base: $35000
  [Pizza] ID = 8, Nombre = 'Veggie Deluxe', Disponible = true (Sabor = Champinones - Pimenton - Cebolla - Maiz, Tamaño = P) | Precio base: $15000
  [Pizza] ID = 9, Nombre = 'Mexicana Picante', Disponible = true (Sabor = Carne - Jalapenos - Nachos triturados, Tamaño = M) | Precio base: $20000
  [Bebida] ID = 10, Nombre = 'Cocacola', Disponible = true (Volumen = 350ml) | Precio base: $4500
  [Bebida] ID = 11, Nombre = 'Sprite', Disponible = true (Volumen = 350ml) | Precio base: $4500
  [Bebida] ID = 12, Nombre = 'Agua', Disponible = true (Volumen = 300ml) | Precio base: $3000
```

---

### Caso 2: Pedido en local (sin costo adicional)
**Acción:** opción `3` --> ID Cliente: `1`, ID Producto: `5`, Cantidad: `2`, Entrega: `1` (Local)

**Resultado esperado:**
```
========================================
           RESUMEN DEL PEDIDO           
========================================
 ID Pedido  : 13
 Cliente    : Juan Diaz
 Producto   : Placer de antano
 Cantidad   : 2
 Entrega    : Para aquí
----------------------------------------
 Subtotal   : $40.000,00
 TOTAL      : $40.000,00
========================================
```

---

### Caso 3: Pedido con domicilio (costo adicional)
**Acción:** opción `3` ---> ID Cliente: `2`, ID Producto: `10`, Cantidad: `1`, Entrega: `3` (Domicilio)

**Resultado esperado:**
```
========================================
           RESUMEN DEL PEDIDO           
========================================
 ID Pedido  : 14
 Cliente    : Martin Marco
 Producto   : Cocacola
 Cantidad   : 1
 Entrega    : Envío a domicilio(+$3000)
----------------------------------------
 Subtotal   : $4.500,00
 TOTAL      : $7.500,00
========================================

```

---

### Caso 4: Entregar Pedido
**Acción:** opción `4`  ---> se muestran pedidos activos ---> ingresar ID del pedido del Caso 2

**Resultado esperado:**
```
--- Pedidos Activos ---
Pedido ID = 13 | Cliente = Juan Diaz | Producto = Placer de antano | Cantidad = 2 | Entrega = Para aquí | Estado del pedido = Pedido en preparación
Confirmación: Pedido 13 marcado como entregado.
```

---

### Caso 5: Cancelar pedido 
**Acción:** opción `5` ---> se muestran pedidos activos ---> ingresar ID del pedido del Caso 3

**Resultado esperado:**
```
--- Pedidos Activos ---
Pedido ID = 14 | Cliente = Martin Marco | Producto = Cocacola | Cantidad = 1 | Entrega = Envío a domicilio | Estado del pedido = Pedido en preparación
ID Pedido a cancelar: 14
Confirmación: Pedido 14 ha sido cancelado.
```

---

### Caso 6: Intentar entregar un pedido ya cancelado
**Acción:** opción `4` ---> Cantidad: `0` o `9`

**Resultado esperado:**
```
Cantidad inválida (1-9).
```

---

### Caso 7: Cliente inexistente
**Acción:** opción `3` ---> ID Cliente: `999`

**Resultado esperado:**
```
Cliente no encontrado.
```

---

## Compilación y ejecución
Se requiere la versión 25 del kit de Java Development (SE) que contiene el compilador (javac) y el ejecutable (java) que permite la ejecución del programa.
Una vez hecho esto, se descarga el proyecto, también se puede hacer solo con el directorio src, se abre la consola y se confirma que la ruta sea dentro de la carpeta del proyecto, se ejecutará entonces:
```bash
# Compilar
javac -d out -sourcepath src src/ui/Main.java
# Ejecutar
java -cp out -ui.Main
```

---

## Lista de chequeo 

- [x] UML completo con relaciones y visibilidad (ver `/docs/uml.png`)
- [x] Clase abstracta `Producto` con subclases `Pizza` y `Bebida`
- [x] Interfaz `Entregable` implementada correctamente
- [x] Polimorfismo con `ArrayList<Producto>`
- [x] Uso correcto de `static` (IdGenerator, Reglas, CalculadoraTotal, TipoEntrega)
- [x] README con 7 casos de prueba
