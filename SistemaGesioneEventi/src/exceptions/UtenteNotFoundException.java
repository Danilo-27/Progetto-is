package exceptions;

public class UtenteNotFoundException extends RuntimeException {
    public UtenteNotFoundException(String message) {
        super(message);
    }
}
