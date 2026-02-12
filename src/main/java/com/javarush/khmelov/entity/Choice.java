package com.javarush.khmelov.entity;

public final class Choice {
    private final String label;
    private final String nextNodeKey;

    public Choice(String label, String nextNodeKey) {
        this.label = label;
        this.nextNodeKey = nextNodeKey;
    }

    public String getLabel() { return label; }
    public String getNextNodeKey() { return nextNodeKey; }
}
