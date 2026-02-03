package com.javarush.chebotarev.quest;

import com.javarush.chebotarev.Base;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CurrentQuestTest extends Base {

    private CurrentQuest currentQuest;

    @BeforeEach
    void createCurrentQuest() {
        try (InputStream inputStream = createInputStream()) {
            Quest quest = questService.loadQuest(inputStream);
            currentQuest = new CurrentQuest(quest);
            currentQuest.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenCurrentNodeIsCommonType_thenMethodIsVictoryThrowsExceptionWithMessage() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> currentQuest.isVictory());
        assertEquals("Current node is common type", exception.getMessage());
    }

    @Test
    void whenMethodNextStageIsCalledWithUnknownNodeId_thenTheMethodThrowsExceptionWithMessage() {
        int unknownNodeId = Integer.MAX_VALUE;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> currentQuest.nextStage(unknownNodeId));
        assertEquals("nextNodeId " + unknownNodeId + " not found", exception.getMessage());
    }

    @Test
    void whenThereIsNoPreviousStage_thenMethodPreviousStageThrowsExceptionWithMessage() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> currentQuest.previousStage());
        assertEquals("No previous stage found", exception.getMessage());
    }
}