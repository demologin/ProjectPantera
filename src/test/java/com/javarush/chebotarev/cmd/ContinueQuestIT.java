package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.BaseIT;
import com.javarush.chebotarev.component.Attribute;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.Utils;
import com.javarush.chebotarev.quest.CurrentQuest;
import com.javarush.chebotarev.quest.Quest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ContinueQuestIT extends BaseIT {

    @Test
    void whenPageIsOpened_thenCommandReturnsJspPage() {
        try (InputStream inputStream = createInputStream()) {
            Quest quest = questService.loadQuest(inputStream);
            try (MockedStatic<Utils> utils = Mockito.mockStatic(Utils.class)) {
                CurrentQuest currentQuest = new CurrentQuest(quest);
                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.CURRENT_QUEST),
                        any()
                )).thenReturn(currentQuest);

                ContinueQuest continueQuest = ObjectRepository.find(ContinueQuest.class);
                String view = continueQuest.doGet(req, servlet);

                assertEquals(Go.NEW_QUEST, view);

                currentQuest = new CurrentQuest(quest);
                currentQuest.start();
                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.CURRENT_QUEST),
                        any()
                )).thenReturn(currentQuest);

                view = continueQuest.doGet(req, servlet);

                assertEquals(Go.QUEST, view);

                currentQuest = new CurrentQuest(quest);
                currentQuest.start();
                currentQuest.nextStage(2);
                when(Utils.extractAttribute(
                        eq(session),
                        eq(Attribute.CURRENT_QUEST),
                        any()
                )).thenReturn(currentQuest);

                view = continueQuest.doGet(req, servlet);

                assertEquals(Go.RESULT, view);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}