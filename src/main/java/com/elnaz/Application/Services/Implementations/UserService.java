package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.User;
import com.elnaz.Application.Data.Repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserService {



        /**
         * Registriert einen neuen Benutzer mit dem gegebenen Benutzernamen.
         * @param username der Benutzername des neuen Benutzers
         * @return true, wenn der Benutzer erfolgreich registriert wurde, false wenn der Benutzername bereits vergeben ist
         */
        boolean registerNewUser(String username);

        /**
         * Sucht einen Benutzer anhand seines Benutzernamens.
         * @param username der Benutzername, nach dem gesucht wird
         * @return Optional mit User, falls gefunden, sonst empty
         */
        Optional<User> findUserByUsername(String username);

        /**
         * Sucht einen Benutzer anhand seiner UUID.
         * @param id die UUID des Benutzers
         * @return Optional mit User, falls gefunden, sonst empty
         */
        Optional<User> findUserById(UUID id);

        /**
         * Löscht einen Benutzer anhand seiner UUID.
         * @param id die UUID des Benutzers, der gelöscht werden soll
         */
        void deleteUserById(UUID id);

        // Du kannst weitere Methoden hinzufügen, z.B. Update, Passwortänderung, etc.
    }



