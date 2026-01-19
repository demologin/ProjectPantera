package com.javarush.golikov.quest.model;

import java.util.ArrayList;
import java.util.List;

public class QuestSession {

    private final String questId;
    private String currentNode;

    // история шагов
    private final List<Step> history = new ArrayList<>();

    // состояние завершения
    private boolean finished = false;
    private boolean win = false;

    public QuestSession(String questId, String startNode) {
        this.questId = questId;
        this.currentNode = startNode;
    }

    public String getQuestId() {
        return questId;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public List<Step> getHistory() {
        return history;
    }

    // добавить шаг в историю
    public void addStep(String questionText, String answerText, boolean positive) {
        history.add(new Step(questionText, answerText, positive));
    }

    // завершение
    public void win() {
        finished = true;
        win = true;
    }

    public void lose() {
        finished = true;
        win = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isWin() {
        return win;
    }
}