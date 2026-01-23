package com.javarush.khmelov.lesson13.quest;

import java.util.Map;

public class QuestConfig {
    private String start;
    private Map<String, SceneConfig> scenes;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Map<String, SceneConfig> getScenes() {
        return scenes;
    }

    public void setScenes(Map<String, SceneConfig> scenes) {
        this.scenes = scenes;
    }
}

