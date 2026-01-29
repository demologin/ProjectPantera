package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserService инициализирован");
    }

    public void registerUser(String username, String password) {
        logger.info("Попытка регистрации пользователя: {}", username);

        if (userRepository.existsByUsername(username)) {
            logger.warn("Пользователь с именем {} уже существует", username);
            throw new UserAlreadyExistsException(username);
        }

        User newUser = new User(username, password);
        userRepository.save(newUser);
        logger.info("Пользователь {} успешно зарегистрирован", username);
    }

    public Optional<User> loginUser(String username, String password) {
        logger.info("Попытка входа пользователя: {}", username);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            logger.warn("Пользователь {} не найден", username);
            throw new UserNotFoundException(username);
        }

        if (!user.get().getPassword().equals(password)) {
            logger.warn("Неверный пароль для пользователя: {}", username);
            throw new InvalidCredentialsException();
        }
        logger.info("Пользователь {} успешно вошёл в систему", username);
        return user;
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean authenticated = session != null && session.getAttribute("username") != null;

        if (authenticated) {
            String username = (String) session.getAttribute("username");
            LoggerFactory.getLogger(UserService.class).debug("Проверка авторизации: пользователь {} авторизован", username);
        }

        return authenticated;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            session.invalidate();
            logger.info("Пользователь {} вышел из системы", username);
        } else {
            logger.debug("Попытка выхода без активной сессии");
        }
    }
}
