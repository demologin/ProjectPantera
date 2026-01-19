package com.javarush.golikov.quest.repository;

import com.javarush.golikov.quest.model.QuestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsRepositoryTest {

    @BeforeEach
    void setUp() {
        StatisticsRepository.clear();
    }

    @Test
    @DisplayName("Test add method adds QuestResult to repository")
    void testAddAddsQuestResultToRepository() {

        QuestResult result =
                new QuestResult("user", "farm", true);

        StatisticsRepository.add(result);

        List<QuestResult> all = StatisticsRepository.all();

        assertEquals(1, all.size());
        assertEquals(result, all.get(0));
    }

    @Test
    @DisplayName("Test all returns unmodifiable list")
    void testAllReturnsUnmodifiableList() {

        StatisticsRepository.add(
                new QuestResult("user", "farm", true)
        );

        List<QuestResult> all = StatisticsRepository.all();

        assertThrows(UnsupportedOperationException.class,
                () -> all.add(new QuestResult("u", "q", false))
        );
    }

    @Test
    @DisplayName("Test clear removes all stored results")
    void testClearRemovesAllStoredResults() {

        StatisticsRepository.add(
                new QuestResult("user", "farm", true)
        );
        StatisticsRepository.add(
                new QuestResult("user", "ufo", false)
        );

        assertEquals(2, StatisticsRepository.all().size());

        StatisticsRepository.clear();

        assertTrue(StatisticsRepository.all().isEmpty());
    }
}