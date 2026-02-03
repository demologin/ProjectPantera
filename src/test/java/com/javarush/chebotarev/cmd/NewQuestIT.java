package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.BaseIT;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.Utils;
import com.javarush.chebotarev.quest.QuestMetadata;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class NewQuestIT extends BaseIT {

    @Test
    void whenPageIsOpened_thenCommandLoadsQuestAndReturnsJspPage() {
        when(req.getParameter(anyString())).thenReturn("0");
        try (InputStream inputStream = createInputStream()) {
            when(servletContext.getResourceAsStream(anyString()))
                    .thenReturn(inputStream);
            try (MockedStatic<Utils> utils = Mockito.mockStatic(Utils.class)) {
                when(Utils.extractAttribute(eq(session), anyString(), any()))
                        .thenReturn(List.of(new QuestMetadata(
                                "Title",
                                "",
                                true
                        )));

                NewQuest newQuest = ObjectRepository.find(NewQuest.class);
                String view = newQuest.doGet(req, servlet);

                assertEquals(Go.NEW_QUEST, view);

                String filepath = createQuestFile();
                when(Utils.extractAttribute(eq(session), anyString(), any()))
                        .thenReturn(List.of(new QuestMetadata(
                                "Title",
                                filepath,
                                false
                        )));

                view = newQuest.doGet(req, servlet);
                deleteQuestFile(filepath);

                assertEquals(Go.NEW_QUEST, view);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}