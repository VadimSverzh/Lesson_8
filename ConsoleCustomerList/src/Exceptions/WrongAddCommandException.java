package Exceptions;

public class WrongAddCommandException extends RuntimeException {
    public WrongAddCommandException(String message) {
        super(message);
    }
}
