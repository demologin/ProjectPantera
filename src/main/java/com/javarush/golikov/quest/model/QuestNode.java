package com.javarush.golikov.quest.model;

import java.util.List;
import java.util.Objects;

public record QuestNode(String id, String text, List<Choice> choices) {

    public QuestNode(String id, String text, List<Choice> choices) {
        this.id = Objects.requireNonNull(id, "Node id must not be null");
        this.text = Objects.requireNonNull(text, "Node text must not be null");
        this.choices = List.copyOf(choices);
    }

    public boolean isFinal() {
        return choices.isEmpty();
    }
}