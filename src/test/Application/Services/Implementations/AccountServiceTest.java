package Services.Implementations;

import Application.Services.Implementations.AccountService;
import Data.Enitites.Account;
import Data.Repositories.AccountRepository;
import Exceptions.AccountNotFoundException;
import Exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void createAccountAsync() throws ExecutionException, InterruptedException {
        Account account = new Account(UUID.randomUUID(), "Test Name", "Test Login", "Test Password");

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccountAsync("Test Name", "Test Login", "Test Password").get();

        assertNotNull(createdAccount);
        assertEquals(account.getName(), createdAccount.getName());
        assertEquals(account.getLogin(), createdAccount.getLogin());
        assertEquals(account.getPassword(), createdAccount.getPassword());
    }

    @Test
    void createAccountAsync_InvalidInput() {
        assertThrows(InvalidInputException.class, () -> {
            accountService.createAccountAsync("", "Test Login", "Test Password").get();
        });
    }

    @Test
    void getAccountAsync() throws ExecutionException, InterruptedException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test Name", "Test Login", "Test Password");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account retrievedAccount = accountService.getAccountAsync(accountId).get();

        assertNotNull(retrievedAccount);
        assertEquals(account.getId(), retrievedAccount.getId());
    }

    @Test
    void getAccountAsync_NotFound() {
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            accountService.getAccountAsync(accountId).get();
        });

        assertTrue(exception.getCause() instanceof AccountNotFoundException);
        assertEquals("Account mit ID " + accountId + " nicht gefunden", exception.getCause().getMessage());
    }

    @Test
    void updateAccountAsync() throws ExecutionException, InterruptedException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test Name", "Test Login", "Test Password");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.updateAccountAsync(account).get();

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void deleteAccountAsync() throws ExecutionException, InterruptedException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test Name", "Test Login", "Test Password");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        doNothing().when(accountRepository).deleteById(accountId);

        accountService.deleteAccountAsync(accountId).get();

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void deleteAccountAsync_NotFound() {
        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            accountService.deleteAccountAsync(accountId).get();
        });

        assertTrue(exception.getCause() instanceof AccountNotFoundException);
        assertEquals("Account mit ID " + accountId + " nicht gefunden", exception.getCause().getMessage());
    }

    @Test
    void updatePasswordAsync() throws ExecutionException, InterruptedException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test Name", "Test Login", "Test Password");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        doAnswer(invocation -> {
            Account updatedAccount = invocation.getArgument(0);
            assertEquals("NewPassword", updatedAccount.getPassword());
            return null;
        }).when(accountRepository).save(any(Account.class));

        accountService.updatePasswordAsync(accountId, "NewPassword").get();

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void getPasswordAsync() throws ExecutionException, InterruptedException {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test Name", "Test Login", "Test Password");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        String password = accountService.getPasswordAsync(accountId).get();

        assertEquals("Test Password", password);
    }

    @Test
    void updatePasswordAsync_NotFound() {
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            accountService.updatePasswordAsync(accountId, "NewPassword").get();
        });

        assertTrue(exception.getCause() instanceof AccountNotFoundException);
        assertEquals("Account mit ID " + accountId + " nicht gefunden", exception.getCause().getMessage());
    }

    @Test
    void getPasswordAsync_NotFound() {
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            accountService.getPasswordAsync(accountId).get();
        });

        assertTrue(exception.getCause() instanceof AccountNotFoundException);
        assertEquals("Account mit ID " + accountId + " nicht gefunden", exception.getCause().getMessage());
    }}
