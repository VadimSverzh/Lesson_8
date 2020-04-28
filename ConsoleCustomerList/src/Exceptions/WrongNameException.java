package Exceptions;

public class WrongNameException extends RuntimeException {
    public WrongNameException (String message){
        super(message);
    }
}
