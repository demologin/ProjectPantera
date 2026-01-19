package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        StatisticsRepository.clear();
        statisticsService = new StatisticsService();
    }

    @Test
    @DisplayName("Test saveResult with win result increments wins counter")
    void testSaveResultWithWinResultIncrementsWinsCounter() {

        statisticsService.saveResult("Гость", "farm", true);

        var stats = statisticsService.getStats();

        assertEquals(1, stats.size());
        assertEquals(1, stats.get(0).getWins());
        assertEquals(0, stats.get(0).getLoses());
    }

    @Test
    @DisplayName("Test saveResult with lose result increments loses counter")
    void testSaveResultWithLoseResultIncrementsLosesCounter() {

        statisticsService.saveResult("Гость", "farm", false);

        var stats = statisticsService.getStats();

        assertEquals(0, stats.get(0).getWins());
        assertEquals(1, stats.get(0).getLoses());
    }

    @Test
    @DisplayName("Test getStats aggregates results by user and quest")
    void testGetStatsAggregatesResultsByUserAndQuest() {

        statisticsService.saveResult("Гость", "farm", true);
        statisticsService.saveResult("Гость", "farm", false);
        statisticsService.saveResult("Гость", "farm", true);

        var row = statisticsService.getStats().get(0);

        assertEquals("Гость", row.getLogin());
        assertEquals("farm", row.getQuestId());
        assertEquals(2, row.getWins());
        assertEquals(1, row.getLoses());
    }

    @Test
    @DisplayName("Test getStats returns empty list when no results exist")
    void testGetStatsReturnsEmptyListWhenNoResultsExist() {

        var stats = statisticsService.getStats();

        assertTrue(stats.isEmpty());
    }

    @Test
    @DisplayName("Test getStats sorts by login")
    void testGetStatsSortsByLogin() {

        statisticsService.saveResult("B", "farm", true);
        statisticsService.saveResult("A", "farm", true);

        var stats = statisticsService.getStats();

        assertEquals("A", stats.get(0).getLogin());
        assertEquals("B", stats.get(1).getLogin());
    }
    @Test
    @DisplayName("Test getStats sorts by questId when login is same")
    void testGetStatsSortsByQuestIdWhenLoginSame() {

        statisticsService.saveResult("Гость", "bQuest", true);
        statisticsService.saveResult("Гость", "aQuest", true);

        var stats = statisticsService.getStats();

        assertEquals("aQuest", stats.get(0).getQuestId());
        assertEquals("bQuest", stats.get(1).getQuestId());
    }

}