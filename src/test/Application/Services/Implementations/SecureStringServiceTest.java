package Services.Implementations;

import Application.Services.Implementations.SecureStringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecureStringServiceTest {
    private SecureStringService secureStringService;

    @BeforeEach
    void setUp() {
        secureStringService = new SecureStringService();
    }

    @Test
    void createSecureString() {
        String input = "TestString";
        String secureString = secureStringService.createSecureString(input);
        assertNotNull(secureString);
    }

    @Test
    void retrieveString() {
        String input = "TestString";
        String secureString = secureStringService.createSecureString(input);
        String retrievedString = secureStringService.retrieveString(secureString);
        assertEquals(input, retrievedString);
    }
}
