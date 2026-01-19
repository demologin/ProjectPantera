package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.model.QuestSession;
import com.javarush.golikov.quest.model.QuestNode;
import com.javarush.golikov.quest.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class QuestServiceTest {

    private QuestService questService;

    @BeforeEach
    void setUp() throws Exception {
        QuestRepository.clear();
        questService = new QuestService();

        InputStream in =
                getClass().getResourceAsStream("/quests/farm.txt");

        QuestRepository.loadTxt(in, "farm");
    }

    @Test
    @DisplayName("Test startQuest creates session with start node")
    void testStartQuestCreatesSessionWithStartNode() {

        QuestSession session = questService.startQuest("farm");

        assertEquals("farm", session.getQuestId());
        assertEquals("start", session.getCurrentNode());
    }

    @Test
    @DisplayName("Test applyChoice changes current node and adds history step")
    void testApplyChoiceChangesCurrentNodeAndAddsHistoryStep() {

        QuestSession session = questService.startQuest("farm");

        questService.applyChoice(session, "barn", "Да");

        assertEquals("barn", session.getCurrentNode());
        assertEquals(1, session.getHistory().size());
    }

    @Test
    @DisplayName("Test isFinalNode returns false for non-final node")
    void testIsFinalNodeReturnsFalseForNonFinalNode() {

        QuestSession session = questService.startQuest("farm");
        QuestNode node = questService.getCurrentNode(session);

        assertFalse(questService.isFinalNode(node));
    }

    @Test
    @DisplayName("Test startQuest throws exception when quest not found")
    void testStartQuestThrowsWhenQuestNotFound() {

        assertThrows(
                IllegalArgumentException.class,
                () -> questService.startQuest("unknown")
        );
    }

    @Test
    @DisplayName("Test isFinalNode returns true for final node")
    void testIsFinalNodeReturnsTrueForFinalNode() {

        QuestSession session = questService.startQuest("farm");

        questService.applyChoice(session, "barn", "Да");

        QuestNode node = questService.getCurrentNode(session);

        assertTrue(questService.isFinalNode(node));
    }

    @Test
    @DisplayName("Test isWinNode detects win node")
    void testIsWinNodeDetectsWinNode() {

        QuestSession session = questService.startQuest("farm");

        questService.applyChoice(session, "barn", "Да");

        QuestNode node = questService.getCurrentNode(session);

        assertTrue(questService.isWinNode(node));
    }

    @Test
    @DisplayName("Test getAllQuests returns all quests")
    void testGetAllQuestsReturnsAllQuests() {

        assertEquals(1, questService.getAllQuests().size());
    }

    @Test
    @DisplayName("Test exitQuest marks session as lost")
    void testExitQuestMarksSessionAsLost() {

        QuestSession session = questService.startQuest("farm");

        questService.exitQuest(session);

        assertTrue(session.isFinished());
        assertFalse(session.isWin());
    }

    @Test
    @DisplayName("Test getQuestTitle returns quest title")
    void testGetQuestTitleReturnsQuestTitle() {

        QuestSession session = questService.startQuest("farm");

        String title = questService.getQuestTitle(session);

        assertNotNull(title);
        assertFalse(title.isBlank());
    }

    @Test
    @DisplayName("Test exitQuest marks unfinished quest as lost")
    void testExitQuestFromUnfinishedQuestMarksLose() {

        QuestSession session = questService.startQuest("farm");

        assertFalse(session.isFinished());

        questService.exitQuest(session);

        assertTrue(session.isFinished());
        assertFalse(session.isWin());
    }
}