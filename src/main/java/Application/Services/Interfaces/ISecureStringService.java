package Application.Services.Interfaces;

/**
 * Interface für den Secure String-Service.
 * Definiert Methoden zum Erstellen und Abrufen sicherer Strings.
 */
public interface ISecureStringService {
    /**
     * Erstellt einen sicheren String.
     * @param input Der Eingabestring.
     * @return Der sichere String.
     */
    String createSecureString(String input);

    /**
     * Ruft den ursprünglichen String aus einem sicheren String ab.
     * @param secureString Der sichere String.
     * @return Der ursprüngliche String.
     */
    String retrieveString(String secureString);
}

