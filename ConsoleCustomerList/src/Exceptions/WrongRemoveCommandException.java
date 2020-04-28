package Exceptions;

public class WrongRemoveCommandException extends RuntimeException {
    public WrongRemoveCommandException(String message) {
        super(message);
    }
}
