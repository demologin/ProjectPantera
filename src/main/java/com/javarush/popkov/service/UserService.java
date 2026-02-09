package com.javarush.popkov.service;

import com.javarush.popkov.entity.User;
import com.javarush.popkov.exception.AppException;
import com.javarush.popkov.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        User loginPattern = User.builder().login(user.getLogin()).build();
        if (userRepository.find(loginPattern).findAny().isEmpty()) {
            userRepository.create(user);
        } else {
            throw new AppException("User with login " + user.getLogin() + " already exists");
        }
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> get(long id) {
        return Optional.ofNullable(userRepository.get(id));
    }

    public Optional<User> get(String login, String password) {
        User patternUser = User
                .builder()
                .login(login)
                .password(password)
                .build();
        return userRepository.find(patternUser).findAny();
    }
}
