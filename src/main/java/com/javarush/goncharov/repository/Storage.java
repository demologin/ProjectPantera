package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.model.Role;
import com.javarush.goncharov.model.User;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Storage {
    private static Storage instance;
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Message> messages = new ConcurrentHashMap<>();

    private Storage() {
        users.put(1L, new User(1L, "Admin", "123", Role.ADMIN));
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
}