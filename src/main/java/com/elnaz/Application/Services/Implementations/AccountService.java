package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.Account;
import com.elnaz.Application.Data.Repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    // Constructor injection
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create account
    public void createAccount(Account account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID());
        }
        accountRepository.save(account);
    }

    // Retrieve account by id
    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    // Retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Update account
    public Account updateAccount(Account account) {
        // Make sure account exists first
        Optional<Account> existing = accountRepository.findById(account.getId());
        if (existing.isPresent()) {
            return accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Account not found for id: " + account.getId());
        }
    }

    // Delete account by id
    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}
