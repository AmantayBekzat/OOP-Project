package exceptions;

public class RetakeLimitExceededException extends RuntimeException {
    public RetakeLimitExceededException(String message) {
        super(message);
    }
}
