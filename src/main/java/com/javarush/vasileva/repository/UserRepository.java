package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User> {

    private final Map<Long, User> map = new HashMap<>();

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

    @Override
    public List<User> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return map.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void create(User user) {
        user.setId(id.incrementAndGet());
        update(user);
    }

    @Override
    public void update(User entity) {
        map.put(entity.getId(), entity);
    }

    @Override
    public void delete(User entity) {
        map.remove(entity.getId());
    }
}
