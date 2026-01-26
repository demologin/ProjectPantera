package com.javarush.goncharov.service;


import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Repository;

import java.util.Map;
import java.util.Optional;

public class UserService {
    private final Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public User get(Long id){
        return userRepository.get(id);
    }

    public Optional<User> find(String login, String password){
        return userRepository.findBy(login, password);
    }

    public void post(User user){
        if (userRepository.findBy(user.getLogin(), user.getPassword())
                .stream()
                .findAny()
                .isEmpty()){
            user.setId(0L);
            userRepository.create(user);
        }
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void update(User user){
        userRepository.update(user);
    }

    public Map<Long, User> getAll(){
        return userRepository.getAll();
    }
}
