package com.javarush.khmelov.lesson13.model;

import lombok.Getter;

public class Player {

    @Getter
    private final String login;
    @Getter
    private final String passwordHash;
    @Getter
    private int gamesPlayed;
    @Getter
    private int wins;
    @Getter
    private int losses;

    public Player(String login, String passwordHash) {

        this.login = login;
        this.passwordHash = passwordHash;
    }

    public boolean checkPassword(String rawPassword) {
        return passwordHash.equals(hash(rawPassword));
    }

    public void recordWin() {
        gamesPlayed++;
        wins++;
    }

    public void recordLoss() {
        gamesPlayed++;
        losses++;
    }

    private static String hash(String input) {
        return Integer.toHexString(input.hashCode());
    }
}
