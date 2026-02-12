package com.javarush.khmelov.entity;

import java.io.Serializable;

public class GameSession implements Serializable {
    private int gamesPlayed;
    private int wins;
    private int losses;

    public int getGamesPlayed() { return gamesPlayed; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }

    public void recordWin() {
        gamesPlayed++;
        wins++;
    }

    public void recordLoss() {
        gamesPlayed++;
        losses++;
    }
}
