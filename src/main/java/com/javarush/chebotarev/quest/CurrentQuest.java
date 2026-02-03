package com.javarush.chebotarev.quest;

import java.util.List;

public class CurrentQuest {

    private final int CANDLE_COUNT_INIT = 3;
    private final Quest quest;
    private Node currentNode = null;
    private Node previousNode = null;
    private int candleCount = CANDLE_COUNT_INIT;

    public CurrentQuest(Quest quest) {
        this.quest = quest;
    }

    public String getTitle() {
        return quest.getTitle();
    }

    public String getPrologue() {
        return quest.getPrologue();
    }

    public String getText() {
        return currentNode.getText();
    }

    public List<Option> getOptions() {
        return currentNode.getOptions();
    }

    public int getCandleCount() {
        return candleCount;
    }

    public boolean isVictory() {
        if (currentNode.isCommonType()) {
            throw new RuntimeException("Current node is common type");
        }
        return currentNode.isVictory();
    }

    public boolean isDone() {
        return ((currentNode != null) && !currentNode.isCommonType());
    }

    public boolean isStarted() {
        return (currentNode != null);
    }

    public boolean hasPreviousStage() {
        return (previousNode != null);
    }

    public void start() {
        currentNode = quest.getFirstNode();
        candleCount = CANDLE_COUNT_INIT;
    }

    public void nextStage(int nextNodeId) {
        checkNextNodeId(nextNodeId);
        previousNode = currentNode;
        currentNode = quest.getNode(nextNodeId);
        if (currentNode == null) {
            throw new RuntimeException("No node with id " + nextNodeId + " exists");
        }
        if (!currentNode.isCommonType() || (candleCount <= 0)) {
            previousNode = null;
        }
    }

    public void previousStage() {
        if (!hasPreviousStage()) {
            throw new RuntimeException("No previous stage found");
        }
        currentNode = previousNode;
        previousNode = null;
        candleCount--;
    }

    private void checkNextNodeId(int nextNodeId) {
        List<Option> options = currentNode.getOptions();
        for (Option option : options) {
            if (option.getNextNodeId() == nextNodeId) {
                return;
            }
        }
        throw new RuntimeException("nextNodeId " + nextNodeId + " not found");
    }
}
