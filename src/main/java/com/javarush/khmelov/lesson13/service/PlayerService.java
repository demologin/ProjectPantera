package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Player;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerService {

    private static final PlayerService INSTANCE = new PlayerService();
    private final Map<String, Player> players = new ConcurrentHashMap<>();

    private PlayerService() {
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }

    public Player getOrCreate(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login must not be empty");
        }
        return players.computeIfAbsent(login, Player::new);
    }

    public Player get(String login) {
        return players.get(login);
    }
    public Collection<Player> getAll() {
        return players.values();
    }
}
