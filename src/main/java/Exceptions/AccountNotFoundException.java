package Exceptions;

/**
 * Ausnahme, die ausgelöst wird, wenn ein Account nicht gefunden wird.
 */

public class AccountNotFoundException  extends RuntimeException {
    /**
     * Konstruktor für AccountNotFoundException.
     * @param message Die Fehlermeldung.
     */
    public AccountNotFoundException(String message) {
        super(message);
    }

}
