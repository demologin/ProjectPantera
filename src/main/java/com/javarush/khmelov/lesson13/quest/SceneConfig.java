package com.javarush.khmelov.lesson13.quest;

import java.util.List;

public class SceneConfig {
    private String text;
    private List<ChoiceConfig> choices;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChoiceConfig> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceConfig> choices) {
        this.choices = choices;
    }
}
