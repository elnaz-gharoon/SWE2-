package com.elnaz.Application.Data.Enitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Category {

    // Primary key with auto-generated UUID
    @Id
    @GeneratedValue
    private UUID id;

    // Category name must be unique and cannot be null
    @Column(nullable = false, unique = true)
    private String name;

    // Default constructor required by JPA
    public Category() {
    }

    // Constructor with name (optional for easier creation)
    public Category(String name) {
        this.name = name;
    }

    // Full constructor (optional)
    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for ID
    public UUID getId() {
        return id;
    }

    // Setter for ID
    public void setId(UUID id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Optional: for debugging or display
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
