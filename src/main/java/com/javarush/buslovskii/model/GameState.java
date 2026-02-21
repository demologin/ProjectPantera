package com.javarush.buslovskii.model;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private String currentQuestId;
    private int currentQuestionId;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private boolean gameOver;
    private boolean victory;

    public GameState() {
        this.currentQuestionId = 1;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.gameOver = false;
        this.victory = false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCurrentQuestId() {
        return currentQuestId;
    }

    public void setCurrentQuestId(String currentQuestId) {
        this.currentQuestId = currentQuestId;
    }

    public int getCurrentQuestionId() {
        return currentQuestionId;
    }

    public void setCurrentQuestionId(int currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

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

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }
}