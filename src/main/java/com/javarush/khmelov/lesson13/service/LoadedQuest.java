package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Scene;

import java.util.Map;

public class LoadedQuest {

    private final String startScene;
    private final Map<String, Scene> scenes;

    public LoadedQuest(String startScene, Map<String, Scene> scenes) {
        this.startScene = startScene;
        this.scenes = scenes;
    }

    public String getStartScene() {
        return startScene;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }
}
