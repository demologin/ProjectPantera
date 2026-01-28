package com.javarush.zyibin.service;

import com.javarush.zyibin.dto.UserTopicStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTopicStatsTest {

    @Test
    void shouldInitializeWithZeroCounters() {

        UserTopicStats stats = new UserTopicStats("Java Core");

        assertEquals(0, stats.getTotal());
        assertEquals(0, stats.getPassed());
        assertEquals(0, stats.getSuccessRate());
    }

    @Test
    void shouldIncrementTotalCounter() {

        UserTopicStats stats = new UserTopicStats("Java Core");

        stats.incrementTotal();
        stats.incrementTotal();

        assertEquals(2, stats.getTotal());
    }

    @Test
    void shouldIncrementPassedCounter() {

        UserTopicStats stats = new UserTopicStats("Java Core");

        stats.incrementPassed();

        assertEquals(1, stats.getPassed());
    }

    @Test
    void shouldCalculateSuccessRateCorrectly() {

        UserTopicStats stats = new UserTopicStats("Java Core");

        stats.incrementTotal();   // 1
        stats.incrementTotal();   // 2
        stats.incrementTotal();   // 3
        stats.incrementPassed();  // 1
        stats.incrementPassed();  // 2

        assertEquals(66, stats.getSuccessRate());
    }

    @Test
    void shouldReturnZeroSuccessRateWhenTotalIsZero() {

        UserTopicStats stats = new UserTopicStats("Java Core");

        assertEquals(0, stats.getSuccessRate());
    }
}
