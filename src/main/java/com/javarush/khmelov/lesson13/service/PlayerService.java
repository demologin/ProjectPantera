package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Player;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerService {

    private static final PlayerService INSTANCE = new PlayerService();
    private final Map<String, Player> players = new ConcurrentHashMap<>();

    private PlayerService() {}

    public void clear() {
        players.clear();
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }

    public Player register(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустым");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        if (players.containsKey(login)) {
            throw new IllegalArgumentException("Игрок с таким логином уже существует");
        }

        Player player = new Player(login, hash(password));
        players.put(login, player);
        return player;
    }

    public Player login(String login, String password) {
        Player player = players.get(login);
        if (player == null) {
            throw new IllegalArgumentException("Игрок не найден");
        }
        if (!player.checkPassword(password)) {
            throw new IllegalArgumentException("Неверный пароль");
        }
        return player;
    }

    public Player findByLogin(String login) {
        return players.get(login);
    }

    public Collection<Player> getAll() {
        return players.values();
    }

    private static String hash(String input) {
        return Integer.toHexString(input.hashCode());
    }
}
