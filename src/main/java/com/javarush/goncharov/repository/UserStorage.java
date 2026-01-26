package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static UserStorage instance;
    private final Map<Long, User> users;

    private UserStorage() {
        users = new HashMap<>();
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