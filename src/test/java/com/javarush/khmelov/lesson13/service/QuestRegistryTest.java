package com.javarush.khmelov.lesson13.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuestRegistryTest {

    @Test
    void questsAreLoaded() {
        Set<String> quests = QuestRegistry.getQuestIds();

        assertFalse(quests.isEmpty());
        assertTrue(quests.contains("space"));
        assertTrue(quests.contains("dungeon"));
    }

    @Test
    void questPathExists() {
        String path = QuestRegistry.getPath("space");

        assertNotNull(path);
        assertTrue(path.endsWith(".yaml"));
    }
}