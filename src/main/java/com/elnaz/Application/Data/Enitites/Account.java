package com.elnaz.Application.Data.Enitites;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

/**
 * Database entity representing an Account.
 * Contains account-related data such as ID, name, login, and password.
 */
public class Account {
    private UUID id;
    private String name;
    private String login;
    private String password;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Constructor for Account.
     *
     * @param id       The unique identifier of the account.
     * @param name     The name of the account holder.
     * @param login    The login username of the account.
     * @param password The password of the account.
     */
    public Account(UUID id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    /**
     * Default constructor.
     */
    public Account() {
    }

    // Getters and setters

    /**
     * Gets the unique identifier of the account.
     *
     * @return The UUID of the account.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     *
     * @param id The UUID to set.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the name of the account holder.
     *
     * @return The account holder's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the account holder.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the login username of the account.
     *
     * @return The login username.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login username of the account.
     *
     * @param login The login username to set.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password of the account.
     *
     * @return The account password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the account.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the Account object.
     *
     * @return A string describing the account.
     */
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
