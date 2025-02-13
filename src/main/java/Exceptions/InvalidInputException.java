package Exceptions;


/**
 * Ausnahme, die ausgelöst wird, wenn ungültige Eingaben gemacht werden.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Konstruktor für InvalidInputException.
     * @param message Die Fehlermeldung.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
