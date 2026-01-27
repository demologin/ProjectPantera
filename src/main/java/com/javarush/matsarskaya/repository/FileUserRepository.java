package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;

import java.util.Optional;

public class FileUserRepository implements UserRepository{
    private final UserFileStorage storage;

    public FileUserRepository(UserFileStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean save(User user) {
        if (storage.userExists(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        storage.saveUser(user.getUsername(), user.getPassword());
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<String> password = storage.getPasswordByUsername(username);
        return password.map(pwd -> new User(username, pwd));
    }

    @Override
    public boolean existsByUsername(String username) {
        return storage.userExists(username);
    }
}
