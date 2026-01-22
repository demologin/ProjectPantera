package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.entity.UserFileStorage;

import java.util.Optional;

public class FileUserRepository implements UserRepository{
    private final UserFileStorage storage;

    public FileUserRepository(UserFileStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean save(User user) {
        storage.saveUser(user.getUsername(), user.getPassword());
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String password = storage.getPasswordByUsername(username);
        if (password != null) {
            return Optional.of(new User(username, password));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByUsername(String username) {
        return storage.userExists(username);
    }
}
