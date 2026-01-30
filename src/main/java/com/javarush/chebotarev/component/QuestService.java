package com.javarush.chebotarev.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.chebotarev.quest.Quest;
import com.javarush.chebotarev.quest.QuestMetadata;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class QuestService {

    private final String SERVER_QUESTS_PATH = "/WEB-INF/quests/";
    private final String userQuestsPath;

    public QuestService() {
        userQuestsPath = System.getProperty("user.home") + File.separator + "quests";
        File dir = new File(userQuestsPath);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new RuntimeException("Could not create user quests directory");
            }
        }
    }

    public void saveQuest(Quest quest) {
        File file;
        do {
            String filename = "quest_" + System.currentTimeMillis() + ".json";
            file = new File(userQuestsPath, filename);
        } while (file.exists());
        ObjectMapper mapper = ObjectRepository.getObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, quest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Quest loadQuest(QuestMetadata questMetadata, ServletContext servletContext) {
        Quest quest;
        ObjectMapper mapper = ObjectRepository.getObjectMapper();
        if (questMetadata.isServerQuest()) {
            InputStream inputStream = servletContext.getResourceAsStream(questMetadata.getPath());
            try {
                quest = mapper.readValue(inputStream, Quest.class);
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File(questMetadata.getPath());
            try {
                quest = mapper.readValue(file, Quest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return quest;
    }

    public List<QuestMetadata> obtainAvailableQuests(ServletContext servletContext) {
        List<QuestMetadata> availableQuests = new ArrayList<>();
        Set<String> resourcePaths = servletContext.getResourcePaths(SERVER_QUESTS_PATH);
        ObjectMapper mapper = ObjectRepository.getObjectMapper();
        for (String path : resourcePaths) {
            InputStream inputStream = servletContext.getResourceAsStream(path);
            Quest quest;
            try {
                quest = mapper.readValue(inputStream, Quest.class);
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            QuestMetadata questMetadata = new QuestMetadata(quest.getTitle(),
                    path,
                    true);
            availableQuests.add(questMetadata);
        }
        File dir = new File(userQuestsPath);
        File[] files = Objects.requireNonNull(dir.listFiles());
        for (File file : files) {
            Quest quest;
            try {
                quest = mapper.readValue(file, Quest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            QuestMetadata questMetadata = new QuestMetadata(quest.getTitle(),
                    file.getAbsolutePath(),
                    false);
            availableQuests.add(questMetadata);
        }
        return availableQuests;
    }
}
