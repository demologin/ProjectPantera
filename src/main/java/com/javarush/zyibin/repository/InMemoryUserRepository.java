package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository{

    private final Map<Long, User> usersById = new ConcurrentHashMap<>();

    private final Map<String, User> usersByUserName = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        usersById.put(user.getId(), user);
        usersByUserName.put(user.getUsername(), user);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(usersByUserName.get(username));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(usersById.get(id));
    }
}
