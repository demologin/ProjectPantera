package com.javarush.golikov.quest.repository;

import com.javarush.golikov.quest.model.Quest;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestRepositoryTest {

    @BeforeEach
    void clearRepositoryBeforeEachTest() {
        QuestRepository.clear();
    }

    @Test
    @DisplayName("Test loadTxt stores quest and get returns it")
    void testLoadTxtStoresQuestAndGetReturnsIt() throws Exception {

        String data = """
            !Test Quest
            *start

            @start
            ? Question
            """;

        QuestRepository.loadTxt(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "test"
        );

        Quest quest = QuestRepository.get("test");

        assertNotNull(quest);
        assertEquals("test", quest.getId());
        assertEquals("Test Quest", quest.getTitle());
    }

    @Test
    @DisplayName("Test all returns all loaded quests")
    void testAllReturnsAllLoadedQuests() throws Exception {

        String data = """
            !Quest
            *start

            @start
            ? Q
            """;

        QuestRepository.loadTxt(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "q1"
        );

        QuestRepository.loadTxt(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "q2"
        );

        assertEquals(2, QuestRepository.all().size());
    }

    @Test
    @DisplayName("Test remove deletes quest by id")
    void testRemoveDeletesQuestById() throws Exception {

        String data = """
            !Quest
            *start

            @start
            ? Q
            """;

        QuestRepository.loadTxt(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "q1"
        );

        QuestRepository.remove("q1");

        assertNull(QuestRepository.get("q1"));
        assertTrue(QuestRepository.all().isEmpty());
    }

    @Test
    @DisplayName("Test clear removes all quests")
    void testClearRemovesAllQuests() throws Exception {

        String data = """
            !Quest
            *start

            @start
            ? Q
            """;

        QuestRepository.loadTxt(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)),
                "q1"
        );

        QuestRepository.clear();

        assertTrue(QuestRepository.all().isEmpty());
    }

    @Test
    @DisplayName("Test get returns null for unknown quest id")
    void testGetReturnsNullForUnknownId() {
        assertNull(QuestRepository.get("unknown"));
    }

    @Test
    @DisplayName("Test loadAll loads quests from servlet context")
    void testLoadAllLoadsQuestsFromServletContext() {

        ServletContext ctx = mock(ServletContext.class);

        when(ctx.getResourcePaths("/WEB-INF/classes/quests/"))
                .thenReturn(Set.of(
                        "/WEB-INF/classes/quests/test1.txt",
                        "/WEB-INF/classes/quests/test2.txt"
                ));

        when(ctx.getResourceAsStream(anyString()))
                .thenReturn(new ByteArrayInputStream("""
                    !Quest
                    *start

                    @start
                    ? Question
                    """.getBytes(StandardCharsets.UTF_8)));

        QuestRepository.loadAll(ctx);

        assertEquals(2, QuestRepository.all().size());
    }

    @Test
    void testLoadAllDoesNothingWhenNoResources() {
        ServletContext ctx = mock(ServletContext.class);
        when(ctx.getResourcePaths("/WEB-INF/classes/quests/"))
                .thenReturn(null);

        QuestRepository.loadAll(ctx);

        assertTrue(QuestRepository.all().isEmpty());
    }

    @Test
    void testRemoveUnknownIdDoesNothing() {
        QuestRepository.remove("unknown");
        assertTrue(QuestRepository.all().isEmpty());
    }

    @Test
    void testLoadAllSkipsWhenResourceStreamIsNull() {

        ServletContext ctx = mock(ServletContext.class);

        when(ctx.getResourcePaths("/WEB-INF/classes/quests/"))
                .thenReturn(Set.of("/WEB-INF/classes/quests/test.txt"));

        when(ctx.getResourceAsStream(anyString()))
                .thenReturn(null);

        QuestRepository.loadAll(ctx);

        assertTrue(QuestRepository.all().isEmpty());
    }
}