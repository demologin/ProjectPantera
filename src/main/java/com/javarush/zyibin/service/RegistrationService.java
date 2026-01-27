package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.validation.UserValidation;
import com.javarush.zyibin.factory.ValidationFactory;

/**
 * Сервис для обработки регистрации пользователей
 * Инкапсулирует всю логику регистрации
 */
public class RegistrationService {
    
    private final UserService userService;
    private final UserValidation userValidator;
    
    public RegistrationService(UserService userService) {
        this.userService = userService;
        this.userValidator = ValidationFactory.createUserValidator();
    }
    
    /**
     * Регистрирует нового пользователя
     * @param username имя пользователя
     * @param password пароль
     * @param email email
     * @return зарегистрированный пользователь
     * @throws ValidationException если данные невалидны
     * @throws IllegalStateException если пользователь уже существует
     */
    public User registerUser(String username, String password, String email) {
        // Валидация данных
        userValidator.validateRegistration(username, password, email);
        
        // Регистрация через UserService
        return userService.register(username, password, email);
    }
}
