package com.javarush.matsarskaya.repository;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.exception.UserAlreadyExistsException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUserRepository implements UserRepository{
    private static final Logger logger = LoggerFactory.getLogger(FileUserRepository.class);
    private final UserFileStorage storage;

    public FileUserRepository(UserFileStorage storage) {
        this.storage = storage;
        logger.info("FileUserRepository инициализирован");
    }

    @Override
    public boolean save(User user) {
        logger.debug("Сохранение пользователя: {}", user.getUsername());
        if (storage.userExists(user.getUsername())) {
            logger.warn("Пользователь {} уже существует в хранилище", user.getUsername());
            throw new UserAlreadyExistsException(user.getUsername());
        }
        storage.saveUser(user.getUsername(), user.getPassword());
        logger.info("Пользователь {} успешно сохранён", user.getUsername());
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        logger.debug("Поиск пользователя по имени: {}", username);
        Optional<String> password = storage.getPasswordByUsername(username);
        if (password.isPresent()) {
            logger.debug("Пользователь {} найден", username);
        } else {
            logger.debug("Пользователь {} не найден", username);
        }
        return password.map(pwd -> new User(username, pwd));
    }

    @Override
    public boolean existsByUsername(String username) {
        logger.debug("Проверка существования пользователя: {}", username);
        return storage.userExists(username);
    }
}
