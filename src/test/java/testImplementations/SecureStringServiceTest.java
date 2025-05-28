package testImplementations;

import com.elnaz.Application.Services.Implementations.SecureStringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SecureStringServiceTest {
    private SecureStringService secureStringService;

    @BeforeEach
    public void setup() {
        secureStringService = new SecureStringService();
    }

    @Test
    public void testCreateSecureString_shouldEncodeString() {
        // Arrange
        String original = "HelloWorld";

        // Act
        String encoded = secureStringService.createSecureString(original);

        // Assert
        assertThat(encoded).isNotNull();
        assertThat(encoded).isNotEmpty();
        assertThat(encoded).isNotEqualTo(original);
    }

    @Test
    public void testCreateSecureString_nullOrEmpty_shouldThrowException() {
        // Assert that null input throws IllegalArgumentException
        assertThatThrownBy(() -> secureStringService.createSecureString(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null or empty");

        // Assert that empty input throws IllegalArgumentException
        assertThatThrownBy(() -> secureStringService.createSecureString("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null or empty");
    }

    @Test
    public void testRetrieveString_shouldDecodeEncodedString() {
        // Arrange
        String original = "HelloWorld";
        String encoded = secureStringService.createSecureString(original);

        // Act
        String decoded = secureStringService.retrieveString(encoded);

        // Assert
        assertThat(decoded).isEqualTo(original);
    }

    @Test
    public void testRetrieveString_nullOrEmpty_shouldThrowException() {
        // Assert that null input throws IllegalArgumentException
        assertThatThrownBy(() -> secureStringService.retrieveString(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null or empty");

        // Assert that empty input throws IllegalArgumentException
        assertThatThrownBy(() -> secureStringService.retrieveString(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null or empty");
    }

    @Test
    public void testRetrieveString_invalidBase64_shouldThrowIllegalStateException() {
        String invalidBase64 = "not-a-valid-base64-string!";

        // Assert that decoding invalid base64 throws IllegalStateException
        assertThatThrownBy(() -> secureStringService.retrieveString(invalidBase64))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Failed to decode");
    }
}
