package com.javarush.zyibin.service;

import com.javarush.zyibin.dto.UserTestStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTestStatsTest {

    @Test
    void shouldInitializeWithZeroCounters() {

        UserTestStats stats = new UserTestStats("Java Core");

        assertEquals(0, stats.getTotal());
        assertEquals(0, stats.getPassed());
        assertEquals(0, stats.getSuccessRate());
    }

    @Test
    void shouldIncrementTotalCounter() {

        UserTestStats stats = new UserTestStats("Java Core");

        stats.incrementTotal();
        stats.incrementTotal();

        assertEquals(2, stats.getTotal());
    }

    @Test
    void shouldIncrementPassedCounter() {

        UserTestStats stats = new UserTestStats("Java Core");

        stats.incrementPassed();

        assertEquals(1, stats.getPassed());
    }

    @Test
    void shouldCalculateSuccessRateCorrectly() {

        UserTestStats stats = new UserTestStats("Java Core");

        stats.incrementTotal();   // 1
        stats.incrementTotal();   // 2
        stats.incrementTotal();   // 3
        stats.incrementPassed();  // 1
        stats.incrementPassed();  // 2

        assertEquals(66, stats.getSuccessRate());
    }

    @Test
    void shouldReturnZeroSuccessRateWhenTotalIsZero() {

        UserTestStats stats = new UserTestStats("Java Core");

        assertEquals(0, stats.getSuccessRate());
    }
}
