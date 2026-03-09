package model;
import exceptions.PizzeriaException;
import services.IdGenerator;

public class Reserva implements Entregable {

    private final int id;
    private final Cliente cliente;
    private final int numPersonas;
    private final String hora;
    private EstadoPedido estado;

    public Reserva(Cliente cliente, int numPersonas, String hora) {
        this.id = IdGenerator.nextId();
        this.cliente = cliente;
        this.numPersonas = numPersonas;
        this.hora = hora;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public int getId() { return this.id; }
    public Cliente getCliente() { return this.cliente; }
    public int getNumPersonas() { return this.numPersonas; }
    public String getHora() { return this.hora; }
    public String getEstado() { return this.estado.getDescripcion(); }

    @Override
    public void pedir() {
        this.estado = EstadoPedido.EN_PROCESO;
    }

    @Override
    public boolean entregar() {
        if (this.estado == EstadoPedido.CANCELADO) {
            throw new PizzeriaException("ERROR: No se puede efectuar una reserva cancelada.");
        }
        this.estado = EstadoPedido.ENTREGADO;
        return true;
    }

    @Override
    public boolean cancelar() {
        if (this.estado == EstadoPedido.ENTREGADO) {
            throw new PizzeriaException("ERROR: No se puede cancelar una reserva ya realizada.");
        }
        this.estado = EstadoPedido.CANCELADO;
        return true;
    }

    @Override
    public boolean estaActivo() {
        return this.estado.getEstadoActividad();
    }

    @Override
    public String toString() {
        return "Reserva ID = " + this.id + " | Cliente = " + this.cliente.getNombre() + " | Personas = " + this.numPersonas
                + " | Hora = " + this.hora + " | Estado = " + this.estado.getDescripcion();
    }
}
