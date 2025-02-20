package Application.Services.Implementations;

import Application.Services.Interfaces.IAccountService;

import Data.Enitites.Account;
import Data.Repositories.AccountRepository;
import Exceptions.AccountNotFoundException;
import Exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This service class is responsible for managing account-related operations,
 * including creating, retrieving, updating, and deleting accounts asynchronously.
 * It interacts with the underlying data storage (AccountRepository) for CRUD operations.
 */
@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    /**
     * Constructor to inject the AccountRepository dependency.
     * @param accountRepository The repository used to interact with account data storage.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Helper method to fetch an account by its ID, throwing an exception if not found.
     * This method eliminates repeated code for fetching accounts.
     * @param id The ID of the account to retrieve.
     * @return The account associated with the given ID.
     * @throws AccountNotFoundException If the account is not found in the repository.
     */
    private Account findAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));
    }

    /**
     * Asynchronously creates a new account with the provided details.
     * The method checks if the inputs are valid (not null or empty) before proceeding.
     * @param name The name of the account holder.
     * @param login The login credentials for the account.
     * @param password The password for the account.
     * @return A CompletableFuture returning the created Account object.
     * @throws InvalidInputException If any input parameter is invalid.
     */
    @Override
    public CompletableFuture<Account> createAccountAsync(String name, String login, String password) {
        if (name == null || name.isEmpty() || login == null || login.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidInputException("Name, login, and password must not be empty.");
        }
        Account account = new Account(UUID.randomUUID(), name, login, password);
        return CompletableFuture.supplyAsync(() -> accountRepository.save(account));
    }

    /**
     * Asynchronously retrieves an account by its ID. This operation calls the helper method
     * to fetch the account and ensure it exists before returning it.
     * @param id The ID of the account to retrieve.
     * @return A CompletableFuture returning the Account object.
     * @throws AccountNotFoundException If the account with the given ID does not exist.
     */
    @Override
    public CompletableFuture<Account> getAccountAsync(UUID id) {
        // Use the helper method to get the account
        Account account = findAccountById(id);
        return CompletableFuture.supplyAsync(() -> account);
    }

    /**
     * Asynchronously retrieves all accounts from the repository.
     * @return A CompletableFuture returning a list of all accounts.
     */
    @Override
    public CompletableFuture<List<Account>> getAccountsAsync() {
        return CompletableFuture.supplyAsync(accountRepository::findAll);
    }

    /**
     * Asynchronously updates an existing account. The method ensures that the account is not null
     * and its ID is valid before saving it back to the repository.
     * @param account The account object to update.
     * @return A CompletableFuture that completes once the account is updated.
     * @throws InvalidInputException If the account or its ID is null.
     */
    @Override
    public CompletableFuture<Void> updateAccountAsync(Account account) {
        if (account == null || account.getId() == null) {
            throw new InvalidInputException("Account and account ID must not be null.");
        }
        return CompletableFuture.runAsync(() -> accountRepository.save(account));
    }

    /**
     * Asynchronously deletes an account by its ID. If the account is not found, an exception is thrown.
     * @param id The ID of the account to delete.
     * @return A CompletableFuture that completes once the account is deleted.
     * @throws AccountNotFoundException If the account with the given ID does not exist.
     */
    @Override
    public CompletableFuture<Void> deleteAccountAsync(UUID id) {
        return CompletableFuture.runAsync(() -> {
            Account account = findAccountById(id);  // Use helper method to check if the account exists
            accountRepository.deleteById(id);
        });
    }

    /**
     * Asynchronously updates the password of an existing account.
     * The account is fetched using the helper method, and then its password is updated.
     * @param id The ID of the account whose password should be updated.
     * @param newPassword The new password to assign to the account.
     * @return A CompletableFuture that completes when the password is updated.
     * @throws AccountNotFoundException If the account does not exist.
     */
    @Override
    public CompletableFuture<Void> updatePasswordAsync(UUID id, String newPassword) {
        return CompletableFuture.runAsync(() -> {
            Account account = findAccountById(id);  // Use helper method to get the account
            account.setPassword(newPassword);
            accountRepository.save(account);
        });
    }

    /**
     * Asynchronously retrieves the password of an account.
     * This method ensures the account exists before returning its password.
     * @param id The ID of the account whose password needs to be retrieved.
     * @return A CompletableFuture returning the account's password.
     * @throws AccountNotFoundException If the account with the given ID does not exist.
     */
    @Override
    public CompletableFuture<String> getPasswordAsync(UUID id) {
        return CompletableFuture.supplyAsync(() -> {
            Account account = findAccountById(id);  // Use helper method to get the account
            return account.getPassword();
        });
    }
}
