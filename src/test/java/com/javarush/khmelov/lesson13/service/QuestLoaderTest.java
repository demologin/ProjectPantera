package com.javarush.khmelov.lesson13.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestLoaderTest {

    @Test
    void questLoadsCorrectly() {
        LoadedQuest quest = QuestLoader.load("quests/dungeon.yaml");

        assertNotNull(quest);
        assertNotNull(quest.getStartScene());
        assertFalse(quest.getScenes().isEmpty());
    }
}
