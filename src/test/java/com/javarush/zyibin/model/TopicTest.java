package com.javarush.zyibin.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopicTest {

    @Test
    void shouldReturnCorrectTopic_whenValidCodeProvided() {

        Topic topic = Topic.fromCode("java-core");

        assertEquals(Topic.JAVA_CORE, topic);
        assertEquals("Java Core", topic.getDisplayName());
    }

    @Test
    void shouldThrowException_whenUnknownCodeProvided() {

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> Topic.fromCode("unknown-topic"));

        assertTrue(exception.getMessage().contains("Unknown topic code"));
    }

    @Test
    void shouldReturnCorrectCodeAndDisplayName() {

        Topic topic = Topic.JUNIT;

        assertEquals("junit5", topic.getCode());
        assertEquals("JUnit 5", topic.getDisplayName());
    }

}
