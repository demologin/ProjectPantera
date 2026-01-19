package com.javarush.khmelov.lesson13.model;

import lombok.Getter;

import java.util.List;

public class Scene {
    @Getter
    private final String id;
    @Getter
    private final String text;
    @Getter
    private final List<Choice> choices;

    public Scene(String id, String text, List<Choice> choices) {
        this.id = id;
        this.text = text;
        this.choices = choices;
    }

}
