package com.javarush.goncharov.service;


import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Repository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> get(Long id){
        return userRepository.get(id);
    }

    public Optional<User> find(String login, String password){
        return userRepository.findBy(login, password);
    }

    public Optional<User> post(User user){
        user.setId(0L);
        userRepository.create(user);
        return Optional.of(user);
    }

    public Boolean delete(User user){
        return userRepository.delete(user);
    }

    public Optional<User> update(User user){
        userRepository.update(user);
        return Optional.of(user);
    }

    public Map<Long, User> getAll(){
        return userRepository.getAll();
    }
}
