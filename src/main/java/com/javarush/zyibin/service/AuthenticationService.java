package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.AuthenticationException;
import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.util.PasswordUtil;
import com.javarush.zyibin.validation.UserValidation;
import com.javarush.zyibin.factory.ValidationFactory;

import java.util.Optional;

/**
 * Сервис для аутентификации пользователей
 */
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final UserValidation userValidator;
    
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userValidator = ValidationFactory.createUserValidator();
    }
    
    /**
     * Аутентифицирует пользователя
     * @param username имя пользователя
     * @param password пароль
     * @return аутентифицированный пользователь
     * @throws ValidationException если данные невалидны
     * @throws AuthenticationException если аутентификация не удалась
     */
    public User authenticate(String username, String password) {
        // Валидация входных данных
        userValidator.validateLogin(username, password);
        
        // Поиск пользователя
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isEmpty()) {
            throw AuthenticationException.userNotFound();
        }
        
        User user = userOptional.get();
        
        // Проверка пароля
        String passwordHash = PasswordUtil.hashPassword(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            throw AuthenticationException.invalidCredentials();
        }
        
        // Проверка блокировки
        if (user.isBlocked()) {
            throw AuthenticationException.userBlocked();
        }
        
        return user;
    }
}
