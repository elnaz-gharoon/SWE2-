package com.elnaz.Application.Data.Enitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * This class represents a User entity mapped to the "users" table in the database.
 */
@Entity
@Table(name = "users")
public class User {

    // Primary key for the User entity (UUID is used for unique identification)
    @Id
    private UUID id;

    // Unique and required username column in the "users" table
    @Column(unique = true, nullable = false)
    private String username;

    public User() {
        // JPA default constructor
    }
    // Parameterized constructor for creating User objects
    public User(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getter and Setter for the user's ID
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
