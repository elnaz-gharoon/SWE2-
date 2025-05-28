package testImplementations;
import com.elnaz.Application.Data.Enitites.Account;
import com.elnaz.Application.Data.Repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class AccountRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_existingAccount_shouldUpdate() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "Name", "login", "pass");

        // Simulate that the account already exists in the database
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(account));

        // Call the save method
        Account saved = accountRepository.save(account);

        // Verify that an UPDATE statement was executed with correct parameters
        verify(jdbcTemplate, times(1)).update(
                eq("UPDATE accounts SET name = ?, login = ?, password = ? WHERE id = ?"),
                eq(account.getName()),
                eq(account.getLogin()),
                eq(account.getPassword()),
                eq(account.getId())
        );

        // Assert that the returned account is the same as the saved one
        assertThat(saved).isEqualTo(account);
    }

    @Test
    public void testSave_newAccount_shouldInsert() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "Name", "login", "pass");

        // Simulate that no account with the given ID exists in the database
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        // Call the save method
        Account saved = accountRepository.save(account);

        // Verify that an INSERT statement was executed with correct parameters
        verify(jdbcTemplate, times(1)).update(
                eq("INSERT INTO accounts (id, name, login, password) VALUES (?, ?, ?, ?)"),
                eq(account.getId()),
                eq(account.getName()),
                eq(account.getLogin()),
                eq(account.getPassword())
        );

        // Assert that the returned account is the same as the saved one
        assertThat(saved).isEqualTo(account);
    }

    @Test
    public void testFindById_found() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "Name", "login", "pass");

        // Simulate finding an account with the given ID
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(account));

        // Call findById
        Optional<Account> found = accountRepository.findById(id);

        // Assert that an account was found and matches the expected account
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(account);
    }

    @Test
    public void testFindById_notFound() {
        UUID id = UUID.randomUUID();

        // Simulate no account found for the given ID
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        // Call findById
        Optional<Account> found = accountRepository.findById(id);

        // Assert that no account was found
        assertThat(found).isNotPresent();
    }

    @Test
    public void testFindAll() {
        List<Account> accounts = Arrays.asList(
                new Account(UUID.randomUUID(), "Name1", "login1", "pass1"),
                new Account(UUID.randomUUID(), "Name2", "login2", "pass2")
        );

        // Simulate returning a list of accounts from the database
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(accounts);

        // Call findAll
        List<Account> result = accountRepository.findAll();

        // Assert that the returned list has the expected size and contents
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(accounts);
    }

    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();

        // Call deleteById
        accountRepository.deleteById(id);

        // Verify that a DELETE statement was executed with the correct ID
        verify(jdbcTemplate, times(1)).update("DELETE FROM accounts WHERE id = ?", id);
    }
}
