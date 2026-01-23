package com.javarush.khmelov.lesson13.model;

import lombok.Getter;

public class Player {

    @Getter
    private final String login;
    @Getter
    private int gamesPlayed;
    @Getter
    private int wins;
    @Getter
    private int losses;

    public Player(String login) {
        this.login = login;
    }

    public void recordWin() {
        gamesPlayed++;
        wins++;
    }

    public void recordLoss() {
        gamesPlayed++;
        losses++;
    }
}
