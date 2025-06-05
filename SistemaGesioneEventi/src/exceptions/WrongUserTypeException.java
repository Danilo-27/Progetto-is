package exceptions;

public class WrongUserTypeException extends Exception {
    public WrongUserTypeException(String message) {
        super(message);
    }
}