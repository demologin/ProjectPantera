package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository extends BaseRepository<User>{
//    private static UserRepository instance;

//    private final Map<Long, User> map = new HashMap<>();
    public static final AtomicLong id = new AtomicLong();

//    private UserRepository() {
//    }

//    public static UserRepository getInstance(){
//        if (instance == null){
//            return new UserRepository();
//        }
//        return instance;
//    }

    @Override
    public User get(long id) {
        return map.get(id);
    }

    @Override
    public Optional<User> findBy(String login) {
        return map.values()
                .stream()
                .filter(u -> u.getLogin().equals(login))
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
