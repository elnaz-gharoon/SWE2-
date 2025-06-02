package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.User;
import com.elnaz.Application.Data.Repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserService {


        /**
         * Registers a new user with the given username.
         *
         * @param username the username of the new user
         * @return true if the user was successfully registered, false if the username is already taken
         */

        boolean registerNewUser(String username);

        /**
         * Searches for a user by their username.
         * @param username the username to search for
         * @return Optional containing the User if found, otherwise empty
         */
        Optional<User> findUserByUsername(String username);

        /**
         * Searches for a user by their UUID.
         * @param id the UUID of the user
         * @return Optional containing the User if found, otherwise empty
         */

        Optional<User> findUserById(UUID id);

        /**
         * Deletes a user by their UUID.
         * @param id the UUID of the user to be deleted
         */

        void deleteUserById(UUID id);

    }



