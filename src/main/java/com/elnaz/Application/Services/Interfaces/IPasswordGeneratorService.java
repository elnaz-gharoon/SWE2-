package com.elnaz.Application.Services.Interfaces;

/**
 * Interface for the Password Generator Service.
 * Defines methods for generating passwords.
 */

public interface IPasswordGeneratorService {
    /**
     * Generates a password with a specified length.
     * @param length The length of the password.
     * @return The generated password.
     */

    String generatePassword(int length);

    /**
     * Generates a password with specific characteristics.
     * @param length The length of the password.
     * @param includeSymbols Whether the password should include symbols.
     * @param includeNumbers Whether the password should include numbers.
     * @param includeUppercase Whether the password should include uppercase letters.
     * @return The generated password.
     */

    String generatePassword(int length, boolean includeSymbols, boolean includeNumbers, boolean includeUppercase);
}

