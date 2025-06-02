package com.elnaz.Application.Data.Repositories;

import com.elnaz.Application.Data.Enitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing User entities in the database.
 *
 * Extends JpaRepository to provide standard CRUD operations for User objects,
 * using UUID as the primary key type.
 *
 * Includes custom query methods:
 * - findByUsername: retrieves a user by their username.
 * - existsByUsername: checks whether a user with the given username exists.
 */

public interface UserRepository extends JpaRepository<User, UUID>
{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
