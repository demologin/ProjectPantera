package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Message;

import java.util.HashMap;
import java.util.Map;

public class MessageStorage {
    private static MessageStorage instance;
    private final Map<Long, Message> messages;

    private MessageStorage() {
        messages = new HashMap<>();
    }

    public static MessageStorage getInstance() {
        if (instance == null) {
            instance = new MessageStorage();
        }
        return instance;
    }

    public Map<Long, Message> getUsers() {
        return messages;
    }
}