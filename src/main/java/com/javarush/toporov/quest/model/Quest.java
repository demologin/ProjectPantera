package com.javarush.toporov.quest.model;

import java.util.HashMap;
import java.util.Map;

public class Quest {
    private String name;
    private String prologue;
    private Map<Integer, QuestStep> steps;

    public Quest(String name) {
        this.name = name;
        this.prologue = prologue;
        this.steps = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getPrologue() { return prologue; }

    public void addStep(QuestStep step) {
        steps.put(step.getId(), step);
    }

    public QuestStep getStep(int id) {
        return steps.get(id);
    }
}
