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
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    // Delete account by id
    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}