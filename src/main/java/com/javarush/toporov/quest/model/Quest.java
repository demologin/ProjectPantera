package com.javarush.toporov.quest.model;

import java.util.HashMap;
import java.util.Map;

public class Quest {
    private String name;
    private Map<Integer, QuestStep> steps;

    public Quest(String name) {
        this.name = name;
        this.steps = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addStep(QuestStep step) {
        steps.put(step.getId(), step);
    }

    public QuestStep getStep(int id) {
        return steps.get(id);
    }
}
