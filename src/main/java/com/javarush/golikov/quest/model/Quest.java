package com.javarush.golikov.quest.model;

import java.util.Map;

public class Quest {
    private final String id;
    private final String title;
    private final String startNode;
    private final Map<String, QuestNode> nodes;

    public Quest(String id, String title, String startNode, Map<String, QuestNode> nodes) {
        this.id = id;
        this.title = title;
        this.startNode = startNode;
        this.nodes = nodes;
    }

    public QuestNode getStart() {
        return nodes.get(startNode);
    }

    public QuestNode getNode(String id) {
        return nodes.get(id);
    }

    public String getTitle() { return title; }

    public String getId() {
        return id;
    }
}
