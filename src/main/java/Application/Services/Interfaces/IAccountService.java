package Application.Services.Interfaces;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import Data.Enitites.Account;

public interface IAccountService {
    CompletableFuture<Account> createAccountAsync(String name, String login, String password);
    CompletableFuture<Account> getAccountAsync(UUID id);
    CompletableFuture<List<Account>> getAccountsAsync();
    CompletableFuture<Void> updateAccountAsync(Account account);
    CompletableFuture<Void> deleteAccountAsync(UUID id);
}

