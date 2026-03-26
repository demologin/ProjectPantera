package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.repository.UserRepository;
import com.javarush.vasileva.util.Helpers;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public void register(String login, String email, String password) {
        User user = User.builder()
                .login(login)
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();
        userRepository.create(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public Optional<User> getValidatedUser(String userIdStr) {
        if (userIdStr == null || userIdStr.isEmpty()) {
            return Optional.empty();
        }
        Long questId = Helpers.parseStringToLong(userIdStr);
        return findById(questId);
    }
}
