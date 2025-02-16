package Application.Services.Implementations;

import Application.Services.Interfaces.IPasswordGeneratorService;

import java.security.SecureRandom;

/**
 * Implementierung des IPasswordGeneratorService-Interfaces.
 * Beinhaltet die Logik zum Generieren von Passwörtern.
 */

public class PasswordGeneratorService implements IPasswordGeneratorService {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generatePassword(int length) {
        return generatePassword(length, true, true, true);
    }

    @Override
    public String generatePassword(int length, boolean includeSymbols, boolean includeNumbers, boolean includeUppercase) {
        StringBuilder password = new StringBuilder(length);
        String charSet = CHAR_LOWER;
        if (includeUppercase) charSet += CHAR_UPPER;
        if (includeNumbers) charSet += NUMBER;
        if (includeSymbols) charSet += OTHER_CHAR;

        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(charSet.length());
            char rndChar = charSet.charAt(rndCharAt);
            password.append(rndChar);
        }
        return password.toString();
    }
}

