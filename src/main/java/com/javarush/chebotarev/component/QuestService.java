package com.javarush.chebotarev.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.chebotarev.quest.Quest;
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

    public void saveQuest(Quest quest, ObjectMapper mapper) {
        File file;
        do {
            String filename = "quest_" + System.currentTimeMillis() + ".json";
            file = new File(userQuestsPath, filename);
        } while (file.exists());
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, quest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> obtainAvailableQuests(ServletContext servletContext) {
        List<String> availableQuests = new ArrayList<>();
        Set<String> resourcePaths = servletContext.getResourcePaths(SERVER_QUESTS_PATH);
        ObjectMapper mapper = ObjectRepository.find(ObjectMapper.class);
        for (String path : resourcePaths) {
            InputStream inputStream = servletContext.getResourceAsStream(path);
            Quest quest;
            try {
                quest = mapper.readValue(inputStream, Quest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            availableQuests.add(quest.getTitle());
        }
        File dir = new File(userQuestsPath);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            Quest quest;
            try {
                quest = mapper.readValue(file, Quest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            availableQuests.add(quest.getTitle());
        }
        return availableQuests;
    }
}
