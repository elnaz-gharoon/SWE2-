package Application.Services.Implementations;

import Application.Services.Interfaces.ISecureStringService;

import java.util.Base64;
/**
 * Implementierung des ISecureStringService-Interfaces.
 * Beinhaltet die Logik zum Erstellen und Abrufen sicherer Strings.
 */
public class SecureStringService implements ISecureStringService {
    @Override
    public String createSecureString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    @Override
    public String retrieveString(String secureString) {
        return new String(Base64.getDecoder().decode(secureString));
    }
}
