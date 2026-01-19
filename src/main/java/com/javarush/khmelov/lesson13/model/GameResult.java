package com.javarush.khmelov.lesson13.model;

import lombok.Getter;

public class GameResult {

    @Getter
    private final String nextSceneId;
    @Getter
    private final boolean gameOver;
    @Getter
    private final boolean win;

    public GameResult(String nextSceneId, boolean gameOver, boolean win) {
        this.nextSceneId = nextSceneId;
        this.gameOver = gameOver;
        this.win = win;
    }
}
