package com.javarush.golikov.quest.repository;

import com.javarush.golikov.quest.model.Quest;
import com.javarush.golikov.quest.loader.TxtQuestLoader;
import jakarta.servlet.ServletContext;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestRepository {

    private static final Logger log =
            Logger.getLogger(QuestRepository.class.getName());

    private static final Map<String, Quest> quests = new HashMap<>();

    public static Collection<Quest> all() {
        return quests.values();
    }

    public static Quest get(String id) {
        return quests.get(id);
    }

    public static void clear() {
        quests.clear();
    }

    public static void loadTxt(InputStream in, String id) throws Exception {
        Quest quest = TxtQuestLoader.load(in, id);
        quests.put(id, quest);
    }

    public static void loadAll(ServletContext ctx) {
        clear();

        try {
            Set<String> files = ctx.getResourcePaths("/WEB-INF/classes/quests/");

            if (files == null) {
                log.warning("Папка quests не найдена");
                return;
            }

            for (String path : files) {
                if (!path.endsWith(".txt")) continue;

                String fileName = path.substring(path.lastIndexOf("/") + 1);
                String questId = fileName.replace(".txt", "");

                try (InputStream in = ctx.getResourceAsStream(path)) {
                    if (in == null) {
                        log.warning("Не удалось загрузить файл: " + path);
                        continue;
                    }
                    loadTxt(in, questId);
                }
            }

            log.info("Квесты успешно загружены: " + quests.keySet());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Ошибка загрузки квестов", e);
        }
    }

    public static void remove(String id) {
        quests.remove(id);
    }
}