package com.javarush.chebotarev.quest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quest {

    private String title;
    private String prologue;
    private List<Node> nodes;

    public String getTitle() {
        return title;
    }

    public String getPrologue() {
        return prologue;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Node getFirstNode() {
        return nodes.get(0);
    }

    public Node getNode(int nodeId) {
        for (Node node : nodes) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        return null;
    }
}
