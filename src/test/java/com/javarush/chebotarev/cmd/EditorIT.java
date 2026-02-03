package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.BaseIT;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.quest.Quest;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class EditorIT extends BaseIT {

    private final Editor editor = ObjectRepository.find(Editor.class);

    @Test
    void whenPageIsOpened_thenCommandReturnsJspPage() {
        String view = editor.doGet(req, servlet);

        assertEquals(Go.EDITOR, view);
    }

    @Test
    void whenPublishQuest_thenCommandCreatesNewQuestFileAndRetursJspPage() {
        try (InputStream inputStream1 = createInputStream();
             InputStream inputStream2 = createInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            inputStream1,
                            StandardCharsets.UTF_8
                    ));
            doNothing().when(req).setCharacterEncoding(anyString());
            when(req.getReader()).thenReturn(bufferedReader);
            Quest dummyQuest = questService.loadQuest(inputStream2);
            String dummyQuestFilepath = questService.saveQuest(dummyQuest);
            File dummyQuestFile = new File(dummyQuestFilepath);
            File[] filesBeforePost = dummyQuestFile.getParentFile().listFiles();
            int fileCountBeforePost = Objects.requireNonNull(filesBeforePost).length;

            String view = editor.doPost(req);
            File[] filesAfterPost = dummyQuestFile.getParentFile().listFiles();
            int fileCountAfterPost = Objects.requireNonNull(filesAfterPost).length;
            boolean equalFilenames;
            for (File fileAP : filesAfterPost) {
                equalFilenames = false;
                for (File fileBP : filesBeforePost) {
                    String filenameAP = fileAP.getName();
                    String filenameBP = fileBP.getName();
                    if (filenameAP.equals(filenameBP)) {
                        equalFilenames = true;
                        break;
                    }
                }
                if (!equalFilenames) {
                    if (!fileAP.delete()) {
                        throw new RuntimeException("Could not delete file: " + fileAP.getAbsolutePath());
                    }
                }
            }
            if (!dummyQuestFile.delete()) {
                throw new RuntimeException("Could not delete file: " + dummyQuestFile.getAbsolutePath());
            }

            assertEquals(Go.ROOT, view);
            assertEquals((fileCountBeforePost + 1), fileCountAfterPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}