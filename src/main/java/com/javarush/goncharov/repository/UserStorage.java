package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static UserStorage instance;
    private final Map<Long, User> users;

    private UserStorage() {
        users = new HashMap<>();
        // Добавим тестового пользователя
//        users.put(1L, new User(1L, "admin", "adminpass"));
//        users.put(2L, new User(2L, "user", "userpass"));
    }

    public static UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Map<Long, User> getUsers() {
        return users;
    }
}