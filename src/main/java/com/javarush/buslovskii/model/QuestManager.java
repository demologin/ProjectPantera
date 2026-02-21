package com.javarush.buslovskii.model;

import com.javarush.buslovskii.model.quests.DetectiveStoryQuest;
import com.javarush.buslovskii.model.quests.SpaceAdventureQuest;
import com.javarush.buslovskii.model.quests.TreasureHuntQuest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class QuestManager {
    private static QuestManager instance;
    private Map<String, Quest> quests;
    private Map<String, Quest> questsById;
    private Map<String, Integer> questStats;

    private QuestManager() {
        quests = new LinkedHashMap<>();
        questsById = new HashMap<>();
        questStats = new ConcurrentHashMap<>();
        initializeQuests();
    }

    public static synchronized QuestManager getInstance() {
        if (instance == null) {
            instance = new QuestManager();
        }
        return instance;
    }

    private void initializeQuests() {
        addQuest(new TreasureHuntQuest());
        addQuest(new SpaceAdventureQuest());
        addQuest(new DetectiveStoryQuest());

    }

    private void addQuest(Quest quest) {
        quests.put(quest.getTitle(), quest);
        questsById.put(quest.getId(), quest);
        questStats.put(quest.getId(), 0);
    }

    public List<Quest> getAllQuests() {
        return new ArrayList<>(quests.values());
    }

    public Quest getQuest(String id) {
        return questsById.get(id);
    }

    public Quest getQuestByTitle(String title) {
        return quests.get(title);
    }

    public void incrementQuestPlays(String questId) {
        questStats.merge(questId, 1, Integer::sum);
    }

    public int getQuestPlays(String questId) {
        return questStats.getOrDefault(questId, 0);
    }

    public Map<String, Integer> getAllQuestStats() {
        return new HashMap<>(questStats);
    }

    public List<Quest> getQuestsByGenre(String genre) {
        List<Quest> result = new ArrayList<>();
        for (Quest quest : quests.values()) {
            if (quest.getGenre().equalsIgnoreCase(genre)) {
                result.add(quest);
            }
        }
        return result;
    }

    public List<Quest> getQuestsByDifficulty(int difficulty) {
        List<Quest> result = new ArrayList<>();
        for (Quest quest : quests.values()) {
            if (quest.getDifficultyLevel() == difficulty) {
                result.add(quest);
            }
        }
        return result;
    }
    public void resetStats() {
        questStats.clear();
    }
}