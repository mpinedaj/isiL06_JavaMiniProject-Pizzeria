package exceptions;

public class PizzeriaException extends RuntimeException {
    public PizzeriaException(String mensaje) {
        super(mensaje);
    }
}