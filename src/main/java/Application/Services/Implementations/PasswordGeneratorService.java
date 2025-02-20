package Application.Services.Implementations;

import Application.Services.Interfaces.IPasswordGeneratorService;
import java.security.SecureRandom;

/**
 * PasswordGeneratorService generates random passwords based on specific criteria.
 * It offers flexibility in choosing the length of the password and whether to include
 * uppercase letters, numbers, and special symbols.
 */
public class PasswordGeneratorService implements IPasswordGeneratorService {

    // Constants for different character sets
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = LOWERCASE.toUpperCase();
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%&*()_+-=[]?";

    // Secure random instance for generating password
    private static final SecureRandom random = new SecureRandom();

    /**
     * Default password generator. Uses lowercase, uppercase, digits, and symbols.
     *
     * @param length The length of the password to generate.
     * @return The generated password.
     */
    @Override
    public String generatePassword(int length) {
        return generatePassword(length, true, true, true);
    }

    /**
     * Generates a password with customizable options for characters.
     *
     * @param length The desired password length.
     * @param includeSymbols Whether to include special characters.
     * @param includeNumbers Whether to include digits.
     * @param includeUppercase Whether to include uppercase letters.
     * @return The generated password.
     */
    @Override
    public String generatePassword(int length, boolean includeSymbols, boolean includeNumbers, boolean includeUppercase) {
        StringBuilder password = new StringBuilder(length);

        // Start with lowercase letters
        StringBuilder charSet = new StringBuilder(LOWERCASE);

        // Add uppercase letters if needed
        if (includeUppercase) charSet.append(UPPERCASE);

        // Add digits if needed
        if (includeNumbers) charSet.append(DIGITS);

        // Add special symbols if needed
        if (includeSymbols) charSet.append(SYMBOLS);

        // Generate a password by picking random characters from the character set
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());  // Pick a random index
            password.append(charSet.charAt(index));  // Add the character to the password
        }

        return password.toString();  // Return the final generated password
    }
}
