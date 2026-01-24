package com.javarush.alimov.quest;

public class GameLogic {

    public static GameResult next(GameState current, String answer) {
        return switch (current) {
            case START -> new GameResult(GameState.UFO_CHALLENGE, null);

            case UFO_CHALLENGE -> {
                if ("accept".equals(answer)) {
                    yield new GameResult(GameState.BRIDGE_CHOICE, null);
                } else {
                    yield new GameResult(GameState.LOSE, "Ты отклонил вызов. Поражение.");
                }
            }

            case BRIDGE_CHOICE -> {
                if ("go".equals(answer)) {
                    yield new GameResult(GameState.IDENTITY_CHOICE, null);
                } else {
                    yield new GameResult(GameState.LOSE, "Ты не пошёл на переговоры. Поражение.");
                }
            }

            case IDENTITY_CHOICE -> {
                if ("truth".equals(answer)) {
                    yield new GameResult(GameState.WIN, "Тебя вернули домой. Победа!");
                } else {
                    yield new GameResult(GameState.LOSE, "Твою ложь разоблачили. Поражение.");
                }
            }

            default -> new GameResult(GameState.START, null);
        };
    }
}

