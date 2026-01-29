package com.javarush.martynov;

public class UserStats {

    private int wins = 0;
    private int losses = 0;

    public void addWin() { wins++; }
    public void addLoss() { losses++; }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getTotal() { return wins + losses; }
}
