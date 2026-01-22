package com.javarush.zyibin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestResultTest {

    private TestResult result;

    @BeforeEach
    void setUp() {
        result = new TestResult(
                1L,
                "java-core",
                10,
                7,
                true,
                LocalDateTime.now()
        );
    }

    @Test
    void shouldCreateTestResultWithGivenValues() {

        assertEquals(1L, result.getUserId());
        assertEquals("java-core", result.getTopicCode());
        assertEquals(10, result.getTotalQuestions());
        assertEquals(7, result.getCorrectAnswers());
        assertTrue(result.isPassed());
        assertNotNull(result.getFinishedAt());
    }

    @Test
    void shouldAllowSettingIdOnceWhenIdIsZero() {

        result.setId(100L);

        assertEquals(100L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenSettingIdSecondTime() {
        result.setId(100L);

        assertThrows(IllegalStateException.class, () -> result.setId(200L));
    }
}
