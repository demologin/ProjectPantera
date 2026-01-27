package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.model.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Storage {
    private static Storage instance;
    private final Map<Long, User> users;
    private final Map<Long, Message> messages;

    private Storage() {
        users = new HashMap<>();
        messages = new HashMap<>();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
}