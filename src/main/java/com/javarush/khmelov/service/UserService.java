package com.javarush.khmelov.service;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.repository.Repository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Transactional
public class UserService {

    private final Repository<User> userRepository;

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
