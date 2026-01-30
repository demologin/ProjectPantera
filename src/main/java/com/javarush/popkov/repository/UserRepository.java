package com.javarush.popkov.repository;

import com.javarush.popkov.entity.Gender;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class UserRepository implements Repository<User> {

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());
    private final Map<Long, User> map = new HashMap<>();

    public UserRepository() {
        map.put(1L, User.builder()
                .id(1L)
                .login("Carl")
                .password("admin")
                .role(Role.ADMIN)
                .gender(Gender.MALE)
                .build());
        map.put(2L, User.builder()
                .id(2L)
                .login("Alisa")
                .password("qwerty")
                .role(Role.USER)
                .gender(Gender.FEMALE)
                .build());
        map.put(3L, User.builder()
                .id(3L)
                .login("Bob")
                .password("")
                .role(Role.GUEST)
                .gender(Gender.MALE)
                .build());
        map.put(4L, User.builder()
                .id(4L)
                .login("Khmelov")
                .password("admin")
                .role(Role.ADMIN)
                .gender(Gender.MALE)
                .build());
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

    public Stream<User> find(User pattern) {
        return map.values()
                .stream()
                .filter(u -> nullOrEquals(pattern.getId(), u.getId()))
                .filter(u -> nullOrEquals(pattern.getLogin(), u.getLogin()))
                .filter(u -> nullOrEquals(pattern.getPassword(), u.getPassword()))
                .filter(u -> nullOrEquals(pattern.getRole(), u.getRole()));
    }

    protected boolean nullOrEquals(Object patternField, Object repoField) {
        return patternField == null || patternField.equals(repoField);
    }
}
