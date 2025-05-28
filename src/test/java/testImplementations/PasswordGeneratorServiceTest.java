package testImplementations;

import com.elnaz.Application.Services.Implementations.PasswordGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Test
    public void testGeneratePassword_defaultSettings_shouldGeneratePasswordOfCorrectLength() {
        int length = 12;

        // Generate password with default settings (includes uppercase, digits, symbols)
        String password = passwordGeneratorService.generatePassword(length);

        // Assert the password is not null and has the expected length
        assertThat(password).isNotNull();
        assertThat(password.length()).isEqualTo(length);
    }
    @Test
    public void testGeneratePassword_onlyLowercase_shouldContainOnlyLowercaseLetters() {
        int length = 10;

        // Generate password with only lowercase letters (no uppercase, digits, symbols)
        String password = passwordGeneratorService.generatePassword(length, false, false, false);

        // Assert the password is of expected length
        assertThat(password.length()).isEqualTo(length);

        // Assert password contains only lowercase letters
        assertThat(password).matches("[a-z]+");
    }
    @Test
    public void testGeneratePassword_includeUppercase_shouldContainUppercaseLetters() {
        int length = 15;

        // Generate password with uppercase letters included
        String password = passwordGeneratorService.generatePassword(length, false, false, true);

        // Assert length is correct
        assertThat(password.length()).isEqualTo(length);

        // Assert password contains at least one uppercase letter
        assertThat(password).matches(".*[A-Z].*");
    }
    @Test
    public void testGeneratePassword_includeNumbers_shouldContainDigits() {
        int length = 15;

        // Generate password with digits included
        String password = passwordGeneratorService.generatePassword(length, false, true, false);

        // Assert length is correct
        assertThat(password.length()).isEqualTo(length);

        // Assert password contains at least one digit
        assertThat(password).matches(".*[0-9].*");
    }

    @Test
    public void testGeneratePassword_includeSymbols_shouldContainSymbols() {
        int length = 20;

        // Generate password with symbols included
        String password = passwordGeneratorService.generatePassword(length, true, false, false);

        // Assert length is correct
        assertThat(password.length()).isEqualTo(length);

        // Assert password contains at least one symbol from SYMBOLS set
        assertThat(password).matches(".*[!@#$%&*()_+\\-\\=\\[\\]?].*");
    }

}

