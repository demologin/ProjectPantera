package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User>{

    private final Map<Long, User> map;
    public static final AtomicLong id = new AtomicLong();

    public UserRepository(UserStorage userStorage) {
        this.map = userStorage.getUsers();
    }

    @Override
    public User get(long id) {
        return map.get(id);
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
    public void create(User user) {
        if (!map.containsKey(user.getId())) {
            user.setId(id.incrementAndGet());
        }
        map.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        map.remove(user.getId());
    }

    @Override
    public void update(User user) {
        if (map.containsKey(user.getId())){
            user.setId(id.incrementAndGet());
            user.setLogin(user.getLogin());
            user.setPassword(user.getPassword());
        }
    }
}
