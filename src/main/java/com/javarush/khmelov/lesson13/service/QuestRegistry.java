package com.javarush.khmelov.lesson13.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QuestRegistry {

    private static final String QUESTS_DIR = "quests";
    private static final Map<String, String> QUESTS = new HashMap<>();

    static {
        loadQuests();
    }

    private static void loadQuests() {
        try {
            ClassLoader classLoader = QuestRegistry.class.getClassLoader();
            URL url = classLoader.getResource(QUESTS_DIR);

            if (url == null) {
                throw new RuntimeException("Quests directory not found in resources");
            }

            Path questsPath = Paths.get(url.toURI());

            try (DirectoryStream<Path> stream =
                         Files.newDirectoryStream(questsPath, "*.yaml")) {

                for (Path file : stream) {
                    String fileName = file.getFileName().toString();
                    String questId = fileName.replace(".yaml", "");
                    QUESTS.put(questId, QUESTS_DIR + "/" + fileName);
                }
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load quests", e);
        }
    }

    public static Set<String> getQuestIds() {
        return QUESTS.keySet();
    }

    public static String getPath(String questId) {
        return QUESTS.get(questId);
    }
}
