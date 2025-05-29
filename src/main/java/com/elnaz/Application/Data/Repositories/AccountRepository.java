package com.elnaz.Application.Data.Repositories;

import com.elnaz.Application.Data.Enitites.Account;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                             "id UUID PRIMARY KEY," +
                             "name VARCHAR(255)," +
                             "login VARCHAR(255)," +
                             "password VARCHAR(255))");
    }

    public Account save(Account account) {
        Optional<Account> existing = findById(account.getId());

        if (existing.isPresent()) {
            jdbcTemplate.update(
                    "UPDATE accounts SET name = ?, login = ?, password = ? WHERE id = ?",
                    account.getName(),
                    account.getLogin(),
                    account.getPassword(),
                    account.getId()
            );
        } else {
            jdbcTemplate.update(
                    "INSERT INTO accounts (id, name, login, password) VALUES (?, ?, ?, ?)",
                    account.getId(),
                    account.getName(),
                    account.getLogin(),
                    account.getPassword()
            );
        }

        return account;
    }

    public Optional<Account> findById(UUID id) {
        List<Account> results = jdbcTemplate.query(
                "SELECT * FROM accounts WHERE id = ?",
                new Object[]{id},
                new AccountRowMapper()
        );
        return results.stream().findFirst();
    }

    public Optional<Account> findByLogin(String login) {
        List<Account> results = jdbcTemplate.query(
                "SELECT * FROM accounts WHERE login = ?",
                new Object[]{login},
                new AccountRowMapper()
        );
        return results.stream().findFirst();
    }

    public List<Account> findAll() {
        return jdbcTemplate.query("SELECT * FROM accounts", new AccountRowMapper());
    }

    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM accounts WHERE id = ?", id);
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Account(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("login"),
                    rs.getString("password")
            );
        }
    }
}
