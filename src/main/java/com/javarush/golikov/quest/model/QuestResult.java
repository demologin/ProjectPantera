package com.javarush.golikov.quest.model;

public record QuestResult(
        String login,
        String questId,
        boolean win
) {}