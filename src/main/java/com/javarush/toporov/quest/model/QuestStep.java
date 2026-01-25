package com.javarush.toporov.quest.model;

import java.util.Map;

public class QuestStep {
    private int id;
    private String text;
    private Map<String, Integer> options;
    private boolean isEnd;
    private boolean isWin;

    public QuestStep(int id, String text, Map<String, Integer> options, boolean isEnd, boolean isWin) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.isEnd = isEnd;
        this.isWin = isWin;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public Map<String, Integer> getOptions() { return options; }
    public boolean isEnd() { return isEnd; }
    public boolean isWin() { return isWin; }
}

