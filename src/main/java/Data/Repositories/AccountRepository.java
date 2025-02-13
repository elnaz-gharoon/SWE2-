package Data.Repositories;

import Data.Enitites.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    /**
     * Speichert einen Account.
     * @param account Der zu speichernde Account.
     * @return Der gespeicherte Account.
     */
    public Account save(Account account) {
        accounts.add(account);
        return account;
    }

    /**
     * Ruft einen Account anhand der ID ab.
     * @param id Die ID des Accounts.
     * @return Ein Optional, das den Account enthält, falls gefunden.
     */
    public Optional<Account> findById(UUID id) {
        return accounts.stream().filter(acc -> acc.getId().equals(id)).findFirst();
    }

    /**
     * Ruft alle Accounts ab.
     * @return Eine Liste aller Accounts.
     */
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    /**
     * Löscht einen Account anhand der ID.
     * @param id Die ID des zu löschenden Accounts.
     */
    public void deleteById(UUID id) {
        accounts.removeIf(acc -> acc.getId().equals(id));
    }
}
