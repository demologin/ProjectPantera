package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Choice;
import com.javarush.khmelov.lesson13.model.GameResult;
import com.javarush.khmelov.lesson13.model.Scene;

import java.util.Map;

public class GameEngine {

    private final Map<String, Scene> scenes;
    private final String startSceneId;

    public GameEngine(String questPath) {
        LoadedQuest quest = QuestLoader.load(questPath);
        this.scenes = quest.getScenes();
        this.startSceneId = quest.getStartScene();
    }

    public Scene getScene(String sceneId) {
        return scenes.get(sceneId);
    }

    public String getStartSceneId() {
        return startSceneId;
    }

    public GameResult makeChoice(String sceneId, String choiceId) {

        Scene currentScene = scenes.get(sceneId);
        if (currentScene == null) {
            return new GameResult(startSceneId, false, false);
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
