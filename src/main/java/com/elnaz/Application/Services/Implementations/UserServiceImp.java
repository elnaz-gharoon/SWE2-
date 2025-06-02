package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.User;
import com.elnaz.Application.Data.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
/**
 * Implementation of the UserService interface, providing user-related business logic.
 *
 * This service is responsible for:
 * - Registering new users if the username is not already taken.
 * - Looking up users by username or ID.
 * - Deleting users by ID.
 *
 * It uses the UserRepository to interact with the underlying database,
 * leveraging Spring Data JPA for data persistence.
 */
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerNewUser(String username) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setUsername(username);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }
}

