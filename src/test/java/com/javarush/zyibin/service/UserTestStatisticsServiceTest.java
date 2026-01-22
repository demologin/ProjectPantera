package com.javarush.zyibin.service;

import com.javarush.zyibin.model.TestResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTestStatisticsServiceTest {

    private final UserTestStatisticsService service =
            new UserTestStatisticsService();

    @Test
    void shouldTreatMixedTestAsSingleTest() {

        TestResult result = new TestResult(
                1L,
                "java-core,servlets",

                10,
                8,
                true,
                LocalDateTime.now()
                );

        List<UserTestStats> stats = service.calculate(List.of(result));

        assertEquals(1, stats.size());

        UserTestStats stat = stats.get(0);
        assertEquals("Java Core, Сервлеты", stat.getTestName());
        assertEquals(1, stat.getTotal());
        assertEquals(1, stat.getPassed());
        assertEquals(100, stat.getSuccessRate());
    }

    @Test
    void shouldAggregateSameTestRunsTogether() {
        TestResult r1 = new TestResult(
                1L,
                "java-core,servlets",
                10,
                6,
                false,
                LocalDateTime.now()
        );

        TestResult r2 = new TestResult(
                1L,
                "java-core,servlets",
                10,
                9,
                true,
                LocalDateTime.now()
        );

        List<UserTestStats> stats =
                service.calculate(List.of(r1, r2));

        assertEquals(1, stats.size());

        UserTestStats stat = stats.get(0);
        assertEquals(2, stat.getTotal());
        assertEquals(1, stat.getPassed());
        assertEquals(50, stat.getSuccessRate());
    }
}
