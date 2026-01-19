package com.javarush.khmelov.lesson13.model;

import lombok.Getter;

public class Choice {

    @Getter
    private final String id;
    @Getter
    private final String text;
    @Getter
    private final String nextSceneId;

    public Choice(String id, String text, String nextSceneId) {
        this.id = id;
        this.text = text;
        this.nextSceneId = nextSceneId;
    }
}
