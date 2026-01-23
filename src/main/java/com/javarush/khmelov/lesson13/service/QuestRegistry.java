package com.javarush.khmelov.lesson13.service;

import java.util.Map;
import java.util.Set;

public class QuestRegistry {

    private static final Map<String, String> QUESTS = Map.of(
            "space", "quests/space_quest.yaml",
            "dungeon", "quests/dungeon.yaml"
    );

    public static Set<String> getQuestIds() {
        return QUESTS.keySet();
    }

    public static String getPath(String questId) {
        return QUESTS.get(questId);
    }
}