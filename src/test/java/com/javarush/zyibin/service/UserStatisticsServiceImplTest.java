package com.javarush.zyibin.service;

import com.javarush.zyibin.model.TestResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserStatisticsServiceImplTest {

    private final UserStatisticsService service =
            new UserStatisticsServiceImpl();

    @Test
    void shouldReturnEmptyStatsForEmptyResults() {
        List<UserTopicStats> stats =
                service.calculateUserTopicStats(List.of());

        assertNotNull(stats);
        assertTrue(stats.isEmpty());
    }

    @Test
    void shouldCountPassedTestForSingleTopic() {

        TestResult result = new TestResult(
                1L,
                "java-core",
                10,
                8,
                true,
                LocalDateTime.now()
        );

        List<UserTopicStats> stats =
                service.calculateUserTopicStats(List.of(result));

        assertEquals(1, stats.size());

        UserTopicStats stat = stats.get(0);
        assertEquals("Java Core", stat.getTopicDisplayName());
        assertEquals(1, stat.getTotal());
        assertEquals(1, stat.getPassed());
        assertEquals(100, stat.getSuccessRate());
    }

    @Test
    void shouldCountFailedTestForSingleTopic() {

        TestResult result = new TestResult(
                1L,
                "java-core",
                10,
                3,
                false,
                LocalDateTime.now()
        );

        List<UserTopicStats> stats =
                service.calculateUserTopicStats(List.of(result));

        UserTopicStats stat = stats.get(0);
        assertEquals(1, stat.getTotal());
        assertEquals(0, stat.getPassed());
        assertEquals(0, stat.getSuccessRate());
    }

    @Test
    void shouldSplitMixedTestIntoSeparateTopics() {

        TestResult result = new TestResult(
                1L,
                "java-core,servlets",
                10,
                9,
                true,
                LocalDateTime.now()
        );

        List<UserTopicStats> stats =
                service.calculateUserTopicStats(List.of(result));

        assertEquals(2, stats.size());

        UserTopicStats javaCore =
                stats.stream()
                        .filter(s -> s.getTopicDisplayName().equals("Java Core"))
                        .findFirst()
                        .orElseThrow();

        UserTopicStats servlets =
                stats.stream()
                        .filter(s -> s.getTopicDisplayName().equals("Сервлеты"))
                        .findFirst()
                        .orElseThrow();

        assertEquals(1, javaCore.getTotal());
        assertEquals(1, javaCore.getPassed());

        assertEquals(1, servlets.getTotal());
        assertEquals(1, servlets.getPassed());
    }

    @Test
    void shouldAggregateMultipleResultsForSameTopic() {

        TestResult r1 = new TestResult(
                1L,
                "java-core",
                10,
                6,
                false,
                LocalDateTime.now()
        );

        TestResult r2 = new TestResult(
                1L,
                "java-core",
                10,
                9,
                true,
                LocalDateTime.now()
        );

        List<UserTopicStats> stats =
                service.calculateUserTopicStats(List.of(r1, r2));

        assertEquals(1, stats.size());

        UserTopicStats stat = stats.get(0);
        assertEquals(2, stat.getTotal());
        assertEquals(1, stat.getPassed());
        assertEquals(50, stat.getSuccessRate());
    }
}
