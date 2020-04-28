package Exceptions;

public class NotInListException extends RuntimeException {
    public NotInListException (String message){
        super(message);
    }
}
