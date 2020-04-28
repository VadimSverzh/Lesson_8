package Exceptions;

public class WrongPhoneException extends RuntimeException {
    public WrongPhoneException (String message){
        super(message);
    }
}
