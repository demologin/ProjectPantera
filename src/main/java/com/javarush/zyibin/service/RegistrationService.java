package com.javarush.zyibin.service;

import com.javarush.zyibin.util.ValidationFactory;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.validation.UserValidation;


public class RegistrationService {

    private final UserService userService;
    private final UserValidation userValidator;

    public RegistrationService(UserService userService) {
        this.userService = userService;
        this.userValidator = ValidationFactory.createUserValidator();
    }

    public User registerUser(String username, String password, String email) {
        userValidator.validateRegistration(username, password, email);

        return userService.register(username, password, email);
    }
}
