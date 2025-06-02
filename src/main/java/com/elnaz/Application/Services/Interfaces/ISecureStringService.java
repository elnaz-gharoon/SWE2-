package com.elnaz.Application.Services.Interfaces;

/**
 * Interface for the Secure String Service.
 * Defines methods for creating and retrieving secure strings.
 */

public interface ISecureStringService {
    /**
            * Creates a secure string.
            * @param input The input string.
            * @return The secure string.
 */

    String createSecureString(String input);

    /**
     * Retrieves the original string from a secure string.
     * @param secureString The secure string.
     * @return The original string.
     */

    String retrieveString(String secureString);
}

