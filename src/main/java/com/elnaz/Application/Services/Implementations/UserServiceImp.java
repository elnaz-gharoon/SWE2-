package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.User;
import com.elnaz.Application.Data.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
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

