package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService implements BaseService<User> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> get(Long id) {
        return userRepository.get(id);
    }
}
