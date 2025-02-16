package Services.Implementations;


import Application.Services.Implementations.PasswordGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorServiceTest {
    private PasswordGeneratorService passwordGeneratorService;

    @BeforeEach
    void setUp() {
        passwordGeneratorService = new PasswordGeneratorService();
    }

    @Test
    void generatePassword() {
        String password = passwordGeneratorService.generatePassword(10);
        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    void generatePassword_WithCustomSettings() {
        String password = passwordGeneratorService.generatePassword(12, true, true, true);
        assertNotNull(password);
        assertEquals(12, password.length());
    }
}

