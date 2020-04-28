package Exceptions;

public class WrongCommandException  extends RuntimeException {
    public WrongCommandException(String message) {
        super(message);
    }
}
