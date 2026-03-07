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
│   ├── Pedible.java 
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
double total = ......
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
pizzeria.registrarCliente(new Cliente("", ""));
pizzeria.registrarProducto(new Pizza("", "", ""));
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
for (Producto p : menu) {          
}
```

---


## E. Clase abstracta

`Producto` es abstracta y obliga a sus subclases a implementar:
- `getTipo()`: devuelve el tipo de producto.
- `calcularPrecioBase()`: cada producto define su propio precio.

También implementa la interfaz `Pedible` con comportamiento común (`pedir()`, `cancelar()`, `estaDisponible()`).

---

## F. Interfaz

```java
public interface Pedible {
    void pedir();
    void cancelar();
    boolean estaDisponible();
}
```

`Producto` implements `Pedible`. Todas las subclases heredan la implementación.

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

---


## Casos de prueba manuales

### Caso 1: Listar menú (polimorfismo)
**Acción:** opción `1`

**Resultado esperado:** muestra Pizza y Bebida con sus precios base distintos.

**Ejemplo:**
```
--- Menú ---
[Pizza] ID=101, Nombre='Napolitana', Disponible=true   | Precio base: $15000
[Pizza] ID=102, Nombre='Mexicana', Disponible=true | Precio base: $25000
[Pizza] ID=103, Nombre='Tres Quesos', Disponible=true | Precio base: $18000
[Bebida] ID=104, Nombre='Coca-Cola', Disponible=true   | Precio base: $5000
[Bebida] ID=105, Nombre='Agua', Disponible=true        | Precio base: $3000
```

---

### Caso 2: Pedido en local (sin costo adicional)
**Acción:** opción `3` --> ID Cliente: `101`, ID Producto: `103`, Cantidad: `1`, Entrega: `1` (Local)

**Resultado esperado:**
```
   Pedido creado: ID=102
   Entrega: Para aquí
   Total:   $18000
```

---

### Caso 3: Pedido con domicilio (costo adicional)
**Acción:** opción `3` ---> ID Cliente: `102`, ID Producto: `101`, Cantidad: `1`, Entrega: `3` (Domicilio)

**Resultado esperado:**
```
   Pedido creado: ID=103
   Entrega: Domicilio (+$2000)
   Total:   $17000
```

---

### Caso 4: Producto no disponible
**Acción:** intentar pedir el mismo producto del Caso 2 (ID Producto: `103` ya fue pedido)

**Resultado esperado:**
```
  El producto no está disponible.
```

---

### Caso 5: Cancelar pedido y liberar producto
**Acción:** opción `4` ---> ID Pedido: `102`

**Resultado esperado:**
```
  Pedido 102 cancelado.
```
Luego listar menú ---> Tres Quesos vuelve a `Disponible=true`.

---

### Caso 6: Cantidad inválida
**Acción:** opción `3` ---> Cantidad: `0` o `11`

**Resultado esperado:**
```
Cantidad inválida (1-10).
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

```bash
# Compilar

# Ejecutar

```

---

## Lista de chequeo 

- [x] UML completo con relaciones y visibilidad (ver `/docs/uml.md`)
- [x] Clase abstracta `Producto` con subclases `Pizza` y `Bebida`
- [x] Interfaz `Pedible` implementada correctamente
- [x] Polimorfismo con `ArrayList<Producto>`
- [x] Uso correcto de `static` (IdGenerator, Reglas, CalculadoraTotal, TipoEntrega)
- [x] README con 7 casos de prueba
