package com.javarush.martynov;

public class GameState {
    private String playerName;
    private int step = 0;
    private int gamesPlayed = 0;
    private String lastMessage;
    private int wins;
    private int losses;

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public GameState() {
    }

    private String deathReason;

    public String getDeathReason() {
        return deathReason;
    }

    public void setDeathReason(String deathReason) {
        this.deathReason = deathReason;
    }

    public void resetGame() {
        this.step = 0;
        this.deathReason = "";
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incrementGames() {
        this.gamesPlayed++;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
