package com.javarush.golikov.quest.model;

public class QuestStatRow {
    private final String login;
    private final String questId;
    private final int wins;
    private final int loses;

    public QuestStatRow(String login, String questId, int wins, int loses) {
        this.login = login;
        this.questId = questId;
        this.wins = wins;
        this.loses = loses;
    }

    public String getLogin() {
        return login;
    }

    public String getQuestId() {
        return questId;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }
}
