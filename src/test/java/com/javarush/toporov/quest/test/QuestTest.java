package com.javarush.toporov.quest.test;

import com.javarush.toporov.quest.model.Quest;
import com.javarush.toporov.quest.model.QuestStep;
import com.javarush.toporov.quest.util.QuestData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestTest {

    @Test
    void testStepRetrieval() {
        Quest quest = QuestData.getQuest("Черная Орхидея");
        QuestStep step = quest.getStep(1);
        assertNotNull(step);
        assertEquals(4, step.getOptions().size());
    }

    @Test
    void testWinStep() {
        Quest quest = QuestData.getQuest("Черная Орхидея");
        QuestStep winStep = quest.getStep(27);
        assertTrue(winStep.isEnd());
        assertTrue(winStep.isWin());
    }
}
