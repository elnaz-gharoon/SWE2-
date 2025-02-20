package Application.Services.Implementations;

import Application.Services.Interfaces.ISecureStringService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * A service for encoding and decoding strings securely using Base64 encoding.
 * This service helps in securely storing and retrieving sensitive information.
 */
@Service
public class SecureStringService implements ISecureStringService {

    /**
     * Encodes a given plain text string using Base64 encoding.
     * Useful for storing sensitive information in a reversible format.
     *
     * @param input The plain text string to encode.
     * @return The Base64-encoded version of the input string.
     * @throws IllegalArgumentException if the input is null or empty.
     */
    @Override
    public String createSecureString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string must not be null or empty.");
        }
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes a Base64-encoded string back to its original plain text format.
     * Used to retrieve sensitive information in its original form.
     *
     * @param secureString The Base64-encoded string to decode.
     * @return The decoded plain text string.
     * @throws IllegalArgumentException if the input is null or empty.
     * @throws IllegalStateException if decoding fails due to an invalid format.
     */
    @Override
    public String retrieveString(String secureString) {
        if (secureString == null || secureString.trim().isEmpty()) {
            throw new IllegalArgumentException("Encoded string must not be null or empty.");
        }
        try {
            return new String(Base64.getDecoder().decode(secureString), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to decode the given string. Ensure it is a valid Base64 format.", e);
        }
    }
}
