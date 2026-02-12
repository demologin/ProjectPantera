package com.javarush.khmelov.entity;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Story {
    private final String code;
    private final String title;
    private final Map<String, StoryNode> nodes;

    public Story(String code, String title, Map<String, StoryNode> nodes) {
        this.code = code;
        this.title = title;
        this.nodes = Collections.unmodifiableMap(new LinkedHashMap<>(nodes));
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public Map<String, StoryNode> getNodes() { return nodes; }

    public StoryNode getNode(String key) {
        StoryNode node = nodes.get(key);
        if (node == null) throw new IllegalArgumentException("Unknown node: " + key);
        return node;
    }
}
