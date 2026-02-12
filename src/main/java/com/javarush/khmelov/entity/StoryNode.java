package com.javarush.khmelov.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StoryNode {
    private final String key;
    private final String text;
    private final List<Choice> choices;
    private final EndingType endingType; // null if not ending

    public StoryNode(String key, String text, List<Choice> choices, EndingType endingType) {
        this.key = key;
        this.text = text;
        this.choices = Collections.unmodifiableList(new ArrayList<>(choices));
        this.endingType = endingType;
    }

    public String getKey() { return key; }
    public String getText() { return text; }
    public List<Choice> getChoices() { return choices; }
    public EndingType getEndingType() { return endingType; }
    public boolean isEnding() { return endingType != null; }
}
