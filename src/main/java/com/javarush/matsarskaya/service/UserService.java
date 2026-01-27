package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Регистрирует нового пользователя.
     * @param username имя пользователя
     * @param password пароль
     * @throws Exception если пользователь уже существует
     */
    public void registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        User newUser = new User(username, password);
        userRepository.save(newUser);
    }

    /**
     * Выполняет вход пользователя в систему.
     * @param username имя пользователя
     * @param password пароль
     * @return Optional с пользователем, если вход успешен
     * @throws Exception если неверные учётные данные
     * @throws Exception если пользователь не найден
     */
    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        if (!user.get().getPassword().equals(password)) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    /**
     * Проверяет, авторизован ли пользователь.
     * @param request HTTP запрос
     * @return true если пользователь авторизован
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("username") != null;
    }

    /**
     * Выполняет выход пользователя из системы.
     * @param request HTTP запрос
     */
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
