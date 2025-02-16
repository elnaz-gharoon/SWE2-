package Data.Enitites;
import java.util.UUID;

/**
 * Datenbank-Entität für einen Account.
 * Beinhaltet die Account-Daten wie ID, Name, Login und Passwort.
 */

public class Account {
    private UUID id;
    private String name;
    private String login;
    private String password;

    /**
     * Konstruktur für Account.
     * @param id Die ID des Accounts.
     * @param name Der Name des Accounts.
     * @param login Der Login des Accounts.
     * @param password Das Passwort des Accounts.
     */
    public Account(UUID id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    // Getter und Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
