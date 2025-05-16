package com.elnaz.Application.Config;

import com.elnaz.Application.Services.Implementations.*;
import com.elnaz.Application.Data.Repositories.AccountRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * Spring configuration class that defines Beans for dependency injection.
 * This class ensures that all required services and repositories are properly initialized and managed by Spring.
 */
@Configuration
@ComponentScan(basePackages = "com.elnaz.Application.Services") // Scans the package for components
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:file:./data/mydb;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE")
                .username("sa")
                .password("")
                .build();
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
