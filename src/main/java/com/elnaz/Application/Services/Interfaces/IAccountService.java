package com.elnaz.Application.Services.Interfaces;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.elnaz.Application.Data.Enitites.Account;
/**
 * Interface for managing account-related operations.
 * Provides asynchronous methods for creating, retrieving,
 * updating, and deleting accounts, as well as handling password management.
 *
 * All methods return CompletableFutures to support non-blocking operations.
 */

public interface IAccountService {
    CompletableFuture<Account> createAccountAsync(String name, String login, String password);
    CompletableFuture<Account> getAccountAsync(UUID id);
    CompletableFuture<List<Account>> getAccountsAsync();
    CompletableFuture<Void> updateAccountAsync(Account account);
    CompletableFuture<Void> deleteAccountAsync(UUID id);
    CompletableFuture<Void> updatePasswordAsync(UUID id, String newPassword);
    CompletableFuture<String> getPasswordAsync(UUID id);
}

