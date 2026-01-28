package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.AuthenticationException;
import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.util.ValidationFactory;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.util.PasswordUtil;
import com.javarush.zyibin.validation.UserValidation;

import java.util.Optional;

public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserValidation userValidator;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userValidator = ValidationFactory.createUserValidator();
    }

    public User authenticate(String username, String password) {
        userValidator.validateLogin(username, password);

        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isEmpty()) {
            throw AuthenticationException.userNotFound();
        }

        User user = userOptional.get();

        String passwordHash = PasswordUtil.hashPassword(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            throw AuthenticationException.invalidCredentials();
        }

        if (user.isBlocked()) {
            throw AuthenticationException.userBlocked();
        }

        return user;
    }
}
