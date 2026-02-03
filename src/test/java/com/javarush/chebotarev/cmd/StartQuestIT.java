package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.BaseIT;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class StartQuestIT extends BaseIT {

    @Test
    void whenPageIsOpened_thenCommandReturnsJspPage() {
        try (InputStream inputStream = createInputStream()) {
            Quest quest = questService.loadQuest(inputStream);
            CurrentQuest currentQuest = new CurrentQuest(quest);
            try (MockedStatic<Utils> utils = Mockito.mockStatic(Utils.class)) {
                when(Utils.extractAttribute(eq(session), anyString(), any()))
                        .thenReturn(currentQuest);

                StartQuest startQuest = ObjectRepository.find(StartQuest.class);
                String view = startQuest.doGet(req, servlet);

                assertEquals(Go.QUEST, view);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}