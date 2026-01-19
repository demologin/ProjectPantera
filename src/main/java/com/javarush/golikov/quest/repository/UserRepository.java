package com.javarush.golikov.quest.repository;

import com.javarush.golikov.quest.auth.Role;
import com.javarush.golikov.quest.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final Map<String, User> users = new HashMap<>();

    static {
        users.put("admin", new User("admin", "admin", Role.ADMIN));
    }

    public static User find(String login) {
        return users.get(login);
    }

    public static Collection<User> all() {
        return users.values();
    }

    public static void save(User u) {
        users.put(u.login(), u);
    }

    public static void delete(String login) {
        users.remove(login);
    }

    public static void clear() {
        users.clear();
    }
}

