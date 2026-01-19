package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Choice;
import com.javarush.khmelov.lesson13.model.GameResult;
import com.javarush.khmelov.lesson13.model.Scene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {

    private final Map<String, Scene> scenes = new HashMap<>();

    public GameEngine() {
        initScenes();
    }

    private void initScenes() {
        scenes.put("start", new Scene(
                "start",
                "Ты потерял память. Принять вызов НЛО?",
                List.of(
                        new Choice("yes", "Принять вызов", "bridge"),
                        new Choice("no", "Отклонить вызов", "lose")
                )
        ));

        scenes.put("bridge", new Scene(
                "bridge",
                "Ты принял вызов. Поднимаешься на мостик к капитану?",
                List.of(
                        new Choice("go", "Подняться на мостик", "captain"),
                        new Choice("stay", "Отказаться", "lose")
                )
        ));

        scenes.put("captain", new Scene(
                "captain",
                "Капитан спрашивает: кто ты?",
                List.of(
                        new Choice("truth", "Рассказать правду", "win"),
                        new Choice("lie", "Солгать", "lose")
                )
        ));

        scenes.put("win", new Scene(
                "win",
                "Тебя вернули домой.Победа!",
                List.of()
        ));
        scenes.put("lose", new Scene(
                "lose",
                "Ты проиграл.",
                List.of()
        ));
    }

    public Scene getScene(String sceneId) {
        return scenes.get(sceneId);
    }

    public GameResult makeChoice(String sceneId, String choiceId) {
        Scene currentScene = scenes.get(sceneId);

        if (currentScene == null) {
            return new GameResult("start", false, false);
        }

        Choice choice = currentScene.getChoices().stream()
                .filter(c -> c.getId().equals(choiceId))
                .findFirst()
                .orElse(null);

        if (choice == null) {
            return new GameResult(sceneId, false, false);
        }

        String nextSceneId = choice.getNextSceneId();

        boolean isGameOver = nextSceneId.equals("win") || nextSceneId.equals("lose");
        boolean isWin = nextSceneId.equals("win");

        return new GameResult(nextSceneId, isGameOver, isWin);
    }
}
