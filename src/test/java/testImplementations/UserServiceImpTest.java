package testImplementations;

import com.elnaz.Application.Data.Enitites.User;
import com.elnaz.Application.Data.Repositories.UserRepository;
import com.elnaz.Application.Services.Implementations.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImpTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }





    @Test
    void findUserByUsername_ShouldReturnUser_IfExists() {
        String username = "john";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    void findUserByUsername_ShouldReturnEmpty_IfNotExists() {
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserByUsername("missing");

        assertFalse(result.isPresent());
    }

    @Test
    void findUserById_ShouldReturnUser_IfExists() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void findUserById_ShouldReturnEmpty_IfNotExists() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteUserById_ShouldCallRepositoryDelete() {
        UUID id = UUID.randomUUID();

        userService.deleteUserById(id);

        verify(userRepository).deleteById(id);
    }
}
