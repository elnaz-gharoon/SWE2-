package Application.Services.Interfaces;

/**
 * Interface für den Passwort-Generator-Service.
 * Definiert Methoden zum Generieren von Passwörtern.
 */
public interface IPasswordGeneratorService {
    /**
     * Generiert ein Passwort mit einer bestimmten Länge.
     * @param length Die Länge des Passworts.
     * @return Das generierte Passwort.
     */
    String generatePassword(int length);

    /**
     * Generiert ein Passwort mit bestimmten Eigenschaften.
     * @param length Die Länge des Passworts.
     * @param includeSymbols Ob das Passwort Symbole enthalten soll.
     * @param includeNumbers Ob das Passwort Zahlen enthalten soll.
     * @param includeUppercase Ob das Passwort Großbuchstaben enthalten soll.
     * @return Das generierte Passwort.
     */
    String generatePassword(int length, boolean includeSymbols, boolean includeNumbers, boolean includeUppercase);
}

