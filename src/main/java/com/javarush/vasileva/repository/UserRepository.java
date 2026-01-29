package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User> {

    private final Map<Long, User> map = new HashMap<>();

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

    public UserRepository() {
        map.put(1L, new User(1L, "Alisa", "alisa@gmail.com","qwerty", Role.USER));
        map.put(2L, new User(2L, "Bob", "bob@gmail.com", "12345", Role.GUEST));
        map.put(3L, new User(3L, "Carl", "admin@gmail.com", "admin", Role.ADMIN));
    }

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
