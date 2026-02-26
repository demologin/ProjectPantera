package com.javarush.ushanov.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Данные игровой сессии игрока.
 * Этот объект хранится в HttpSession — то есть живёт на сервере
 * и привязан к конкретному браузеру/пользователю.
 *
 * Мы храним здесь:
 *  - имя игрока (вводится на стартовой странице)
 *  - id текущего шага квеста
 *  - количество сыгранных игр
 *  - количество побед
 */
@Getter
@Setter
public class GameSession {

    private String playerName;
    private int currentStepId;
    private int gamesPlayed;
    private int gamesWon;

    public GameSession(String playerName, int startStepId) {
        this.playerName = playerName;
        this.currentStepId = startStepId;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    /** Вызывается при начале новой игры — сбрасывает шаг на стартовый */
    public void startNewGame(int startStepId) {
        this.currentStepId = startStepId;
        this.gamesPlayed++;
    }

    /** Вызывается при победе */
    public void registerWin() {
        this.gamesWon++;
    }
}
