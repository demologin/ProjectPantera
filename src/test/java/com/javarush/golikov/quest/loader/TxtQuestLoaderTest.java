package com.javarush.golikov.quest.loader;

import com.javarush.golikov.quest.model.Choice;
import com.javarush.golikov.quest.model.Quest;
import com.javarush.golikov.quest.model.QuestNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class TxtQuestLoaderTest {

    @Test
    @DisplayName("Test load valid quest returns Quest with title and start node")
    void testLoadValidQuestReturnsQuestWithStartNode() throws Exception {

        String data = """
            !Farm Quest
            *start

            @start
            ? You are on a farm
            + Open barn -> win
            - Run away -> lose

            @win
            ? You win!

            @lose
            ? You lose!
            """;

        Quest quest = TxtQuestLoader.load(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "farm"
        );

        assertNotNull(quest);
        assertEquals("farm", quest.getId());
        assertEquals("Farm Quest", quest.getTitle());

        QuestNode start = quest.getNode("start");
        assertNotNull(start);
        assertEquals(2, start.choices().size());
    }

    @Test
    @DisplayName("Test quest without title throws RuntimeException")
    void testLoadQuestWithoutTitleThrowsException() {

        String data = """
            *start

            @start
            ? Question
            """;

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                        "bad"
                )
        );

        assertEquals("Quest has no title (!)", ex.getMessage());
    }

    @Test
    @DisplayName("Test quest without start node throws RuntimeException")
    void testLoadQuestWithoutStartNodeThrowsException() {

        String data = """
            !No Start Quest

            @intro
            ? Just text
            """;

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                        "bad"
                )
        );

        assertEquals("Quest has no start node (*)", ex.getMessage());
    }

    @Test
    @DisplayName("Test node without text throws RuntimeException")
    void testNodeWithoutTextThrowsException() {

        String data = """
            !Bad Quest
            *start

            @start
            + Go -> win
            """;

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                        "bad"
                )
        );

        assertTrue(ex.getMessage().startsWith("Node has no text"));
    }

    @Test
    @DisplayName("Test invalid choice format throws RuntimeException")
    void testInvalidChoiceFormatThrowsException() {

        String data = """
            !Bad Choice Quest
            *start

            @start
            ? Question
            + Invalid format
            """;

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                        "bad"
                )
        );

        assertTrue(ex.getMessage().startsWith("Invalid choice"));
    }

    @Test
    @DisplayName("Test positive and negative choices parsed correctly")
    void testPositiveAndNegativeChoicesParsedCorrectly() throws Exception {

        String data = """
            !Test Quest
            *start

            @start
            ? Question
            + Yes -> win
            - No -> lose

            @win
            ? You win

            @lose
            ? You lose
            """;

        Quest quest = TxtQuestLoader.load(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "test"
        );

        QuestNode start = quest.getNode("start");

        assertNotNull(start);
        assertEquals(2, start.choices().size());

        Choice first = start.choices().get(0);
        Choice second = start.choices().get(1);

        assertTrue(first.positive(), "First choice must be positive (+)");
        assertFalse(second.positive(), "Second choice must be negative (-)");
    }

    @Test
    void testLoadEmptyFileThrowsException() {
        String data = "";

        assertThrows(Exception.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );
    }

    @Test
    void testLoadWithoutStartThrowsException() {
        String data = """
        !Quest
        @node
        ? Question
        """;

        assertThrows(Exception.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );
    }

    @Test
    void testLoadIgnoresBlankLines() throws Exception {
        String data = """

        !Quest

        *start


        @start

        ? Question

        """;

        Quest quest = TxtQuestLoader.load(
                new ByteArrayInputStream(data.getBytes()),
                "id"
        );

        assertNotNull(quest);
    }
    @Test
    void testLoadWithoutTitleThrowsException() {
        String data = """
        *start

        @start
        ? Question
        """;

        assertThrows(Exception.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );
    }

    @Test
    void testTextBeforeNodeThrowsException() {
        String data = """
        !Quest
        *start
        ? text before node
        """;

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );

        assertTrue(ex.getMessage().startsWith("Text before node id"));
    }


    @Test
    void testChoiceBeforeNodeThrowsException() {
        String data = """
        !Quest
        *start
        + Go -> win
        """;

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );

        assertTrue(ex.getMessage().startsWith("Choice before node id"));
    }

    @Test
    void testLastNodeWithoutTextThrowsException() {
        String data = """
        !Quest
        *start

        @start
        ? ok

        @end
        """;

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );

        assertTrue(ex.getMessage().contains("Node 'end' has no text"));
    }

    @Test
    void testStartNodeNotFoundThrowsException() {
        String data = """
        !Quest
        *start

        @other
        ? text
        """;

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                TxtQuestLoader.load(
                        new ByteArrayInputStream(data.getBytes()),
                        "id"
                )
        );

        assertTrue(ex.getMessage().startsWith("Start node not found"));
    }
}