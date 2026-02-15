package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User>{

    private final Map<Long, User> map;
    public static final AtomicLong id = new AtomicLong(2);

    public UserRepository(Storage userStorage) {
        this.map = userStorage.getUsers();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<User> findBy(String login, String password) {
        return map.values()
                .stream()
                .filter(u -> u.getLogin().equals(login))
                .filter(u -> u.getPassword().equals(password))
                .findAny();
    }

    @Override
    public Map<Long, User> getAll() {
        return map;
    }

    @Override
    public Optional<User> create(User user) {
        if (!map.containsKey(user.getId())) {
            user.setId(id.incrementAndGet());
        }
        map.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public Boolean delete(User user) {
        int sizeBeforeDelete = map.size();
        map.remove(user.getId());
        int sizeAfterDelete = map.size();
        return sizeBeforeDelete > sizeAfterDelete ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Optional<User> update(User user) {
        map.get(user.getId()).setLogin(user.getLogin());
        map.get(user.getId()).setPassword(user.getPassword());
        map.get(user.getId()).setRole(user.getRole());
        map.get(user.getId()).setEmail(user.getEmail());
        return Optional.of(user);
    }
}
