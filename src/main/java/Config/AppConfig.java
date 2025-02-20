package Config;

import Application.Services.Implementations.*;
import Data.Repositories.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Spring configuration class that defines Beans for dependency injection.
 * This class ensures that all required services and repositories are properly initialized and managed by Spring.
 */
@Configuration
@ComponentScan(basePackages = "Application.Services") // Scans the package for components
public class AppConfig {

    /**
     * Creates and provides an AccountRepository Bean.
     * This repository handles database operations related to accounts.
     *
     * @return an instance of AccountRepository
     */
    @Bean
    @Scope("singleton") // Ensures only one instance exists
    public AccountRepository accountRepository() {
        return new AccountRepository();
    }

    /**
     * Creates and provides an AccountService Bean with AccountRepository injected.
     *
     * @param accountRepository The repository used for account management
     * @return an instance of AccountService
     */
    @Bean
    @Scope("singleton")
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountService(accountRepository);
    }

    /**
     * Creates and provides a PasswordGeneratorService Bean.
     *
     * @return an instance of PasswordGeneratorService
     */
    @Bean
    public PasswordGeneratorService passwordGeneratorService() {
        return new PasswordGeneratorService();
    }

    /**
     * Creates and provides a SecureStringService Bean.
     *
     * @return an instance of SecureStringService
     */
    @Bean
    public SecureStringService secureStringService() {
        return new SecureStringService();
    }
}
