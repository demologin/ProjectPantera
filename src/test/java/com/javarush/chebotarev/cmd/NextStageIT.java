package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.BaseIT;
import com.javarush.chebotarev.component.*;
import com.javarush.chebotarev.quest.CurrentQuest;
import com.javarush.chebotarev.quest.Quest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class NextStageIT extends BaseIT {

    @Test
    void whenPageIsOpened_thenCommandSwitchesStageAndReturnsJspPage() {
        try (InputStream inputStream = createInputStream()) {
            Quest quest = questService.loadQuest(inputStream);
            CurrentQuest currentQuest = new CurrentQuest(quest);
            currentQuest.start();
            Statistics statistics = new Statistics();
            try (MockedStatic<Utils> utils = Mockito.mockStatic(Utils.class)) {
                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.STATISTICS),
                        any()
                )).thenReturn(statistics);

                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.CURRENT_QUEST),
                        any()
                )).thenReturn(currentQuest);
                when(req.getParameter(anyString())).thenReturn("2");

                NextStage nextStage = ObjectRepository.find(NextStage.class);
                String view = nextStage.doGet(req, servlet);

                assertEquals(Go.RESULT, view);
                assertEquals(1, statistics.getVictoriesCount());
                assertEquals(0, statistics.getDefeatsCount());

                currentQuest = new CurrentQuest(quest);
                currentQuest.start();
                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.CURRENT_QUEST),
                        any()
                )).thenReturn(currentQuest);
                when(req.getParameter(anyString())).thenReturn("3");

                view = nextStage.doGet(req, servlet);

                assertEquals(Go.RESULT, view);
                assertEquals(1, statistics.getVictoriesCount());
                assertEquals(1, statistics.getDefeatsCount());

                currentQuest = new CurrentQuest(quest);
                currentQuest.start();
                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.CURRENT_QUEST),
                        any()
                )).thenReturn(currentQuest);
                when(req.getParameter(anyString())).thenReturn("4");

                view = nextStage.doGet(req, servlet);

                assertEquals(Go.QUEST, view);
                assertEquals(1, statistics.getVictoriesCount());
                assertEquals(1, statistics.getDefeatsCount());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}