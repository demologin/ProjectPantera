package com.javarush.toporov.quest.util;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("admin", "admin");
    }

    public static boolean register(String login, String password) {
        if (login == null || login.isEmpty() || users.containsKey(login)) {
            return false; // Пользователь уже есть или логин пустой
        }
        users.put(login, password);
        return true;
    }

    public static void save(String login, String password) {
        users.put(login, password);
    }

    public static boolean check(String login, String password) {
        return users.containsKey(login) && users.get(login).equals(password);
    }
}
