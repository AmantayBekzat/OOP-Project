package exceptions;

public class InvalidSupervisorException extends RuntimeException {
    public InvalidSupervisorException(String message) {
        super(message);
    }
}
