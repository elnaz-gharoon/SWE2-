package testImplementations;

import com.elnaz.Application.Data.Enitites.Account;
import com.elnaz.Application.Data.Repositories.AccountRepository;
import com.elnaz.Application.Exceptions.AccountNotFoundException;
import com.elnaz.Application.Services.Implementations.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the AccountService class.
 *
 * This test suite verifies the core functionalities of AccountService, including:
 * - Creating accounts (with and without pre-set IDs)
 * - Retrieving accounts by ID (found and not found cases)
 * - Retrieving all accounts
 * - Updating accounts
 * - Deleting accounts
 *
 * The tests use Mockito to mock the AccountRepository dependency and ensure
 * that AccountService behaves correctly under various scenarios.
 */

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Initializes mocks and injects them into the AccountService instance
    }

    @Test
    public void testCreateAccount_withNullId_shouldSetUUID() {
        // Given: an account with a null ID
        Account account = new Account();
        account.setId(null);

        // When: creating the account
        accountService.createAccount(account);

        // Then: a UUID should be assigned and the account should be saved
        assertThat(account.getId()).isNotNull();
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testCreateAccount_withId_shouldNotOverrideId() {
        // Given: an account with a pre-set ID
        UUID id = UUID.randomUUID();
        Account account = new Account();
        account.setId(id);

        // When: creating the account
        accountService.createAccount(account);

        // Then: the original ID should be preserved
        assertThat(account.getId()).isEqualTo(id);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testGetAccountById_found() {
        // Given: an account that exists in the repository
        UUID id = UUID.randomUUID();
        Account account = new Account();
        account.setId(id);

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        // When: retrieving the account by ID
        Optional<Account> found = accountService.getAccountById(id);

        // Then: the account should be found and returned
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(id);
    }

    @Test
    public void testGetAccountById_notFound() {
        // Given: a non-existent account ID
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // When: trying to retrieve the account
        Optional<Account> found = accountService.getAccountById(id);

        // Then: result should be empty
        assertThat(found).isNotPresent();
    }

    @Test
    public void testGetAllAccounts() {
        // Given: the repository returns a list of two accounts
        List<Account> accounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        // When: retrieving all accounts
        List<Account> result = accountService.getAllAccounts();

        // Then: result should contain exactly two accounts
        assertThat(result).hasSize(2);
    }

    @Test
    public void testUpdateAccount() {
        // When: updating an account
        Account account = new Account();
        accountService.updateAccount(account);

        // Then: the account should be saved via the repository
        verify(accountRepository, times(1)).save(account);
    }


    @Test
    public void testDeleteAccount() {
        // Given: a specific account ID
        UUID id = UUID.randomUUID();

        // When: deleting the account
        accountService.deleteAccount(id);

        // Then: the repository should delete the account by ID
        verify(accountRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetAccountById_shouldCallRepositoryWithCorrectId() {
        // Generate a random UUID to use as the account ID for this test
        UUID id = UUID.randomUUID();

        // Mock the repository to return an empty Optional when searched with the given ID
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // Call the service method to retrieve an account by ID
        accountService.getAccountById(id);

        // Verify that the repository's findById method was called exactly once with the correct ID
        verify(accountRepository, times(1)).findById(id);
    }

}
