package Config;

import Application.Services.Implementations.AccountService;
import Application.Services.Implementations.PasswordGeneratorService;
import Application.Services.Implementations.SecureStringService;
import Data.Repositories.AccountRepository;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AppConfig {

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepository();
    }

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountService(accountRepository);
    }
}




