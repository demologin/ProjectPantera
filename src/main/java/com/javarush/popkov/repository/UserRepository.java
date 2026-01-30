package com.javarush.popkov.repository;

import com.javarush.popkov.entity.Gender;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User> {

    private final Map<Long, User> map = new HashMap<>();

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

    public UserRepository() {
        map.put(1L, new User(1L, "Alisa", "qwerty", Role.USER, Gender.FEMALE, null));
        map.put(2L, new User(2L, "Bob", "", Role.GUEST, Gender.MALE, null));
        map.put(3L, new User(3L, "Carl", "admin", Role.ADMIN, Gender.MALE, null));
        map.put(4L, new User(4L, "Khmelov", "admin", Role.ADMIN, Gender.MALE, null));
    }

    @Override
    public Collection<User> getAll() {
        return map.values();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create(User entity) {
        entity.setId(id.incrementAndGet());
        update(entity);
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
