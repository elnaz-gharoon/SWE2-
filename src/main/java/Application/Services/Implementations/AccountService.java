package Application.Services.Implementations;

import Application.Services.Interfaces.IAccountService;
import Data.Enitites.Account;
import Data.Repositories.AccountRepository;
import Exceptions.AccountNotFoundException;
import Exceptions.InvalidInputException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public CompletableFuture<Account> createAccountAsync(String name, String login, String password) {
        if (name == null || name.isEmpty() || login == null || login.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidInputException("Name, Login und Passwort dürfen nicht leer sein.");
        }
        Account account = new Account(UUID.randomUUID(), name, login, password);
        return CompletableFuture.supplyAsync(() -> accountRepository.save(account));
    }

    @Override
    public CompletableFuture<Account> getAccountAsync(UUID id) {
        return CompletableFuture.supplyAsync(() -> accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account mit ID " + id + " nicht gefunden")));
    }

    @Override
    public CompletableFuture<List<Account>> getAccountsAsync() {
        return CompletableFuture.supplyAsync(accountRepository::findAll);
    }

    @Override
    public CompletableFuture<Void> updateAccountAsync(Account account) {
        if (account == null || account.getId() == null) {
            throw new InvalidInputException("Account und Account-ID dürfen nicht null sein.");
        }
        return CompletableFuture.runAsync(() -> accountRepository.save(account));
    }

    @Override
    public CompletableFuture<Void> deleteAccountAsync(UUID id) {
        return CompletableFuture.runAsync(() -> {
            if (!accountRepository.findById(id).isPresent()) {
                throw new AccountNotFoundException("Account mit ID " + id + " nicht gefunden");
            }
            accountRepository.deleteById(id);
        });
    }

    public CompletableFuture<Void> updatePasswordAsync(UUID id, String newPassword) {
        return CompletableFuture.runAsync(() -> {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account mit ID " + id + " nicht gefunden"));
            account.setPassword(newPassword);
            accountRepository.save(account);
        });
    }

    public CompletableFuture<String> getPasswordAsync(UUID id) {
        return CompletableFuture.supplyAsync(() -> {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account mit ID " + id + " nicht gefunden"));
            return account.getPassword();
        });
    }
}