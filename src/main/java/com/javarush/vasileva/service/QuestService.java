package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.repository.QuestRepository;
import com.javarush.vasileva.repository.Repository;

import java.util.List;
import java.util.Optional;

public class QuestService {
    private final Repository<Quest> questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public List<Quest> getAll() {
        return questRepository.getAll();
    }

    public Optional<Quest> get(Long id) {
        return questRepository.get(id);
    }

    public void create(Quest quest) {
        questRepository.create(quest);
    }

    public void update(Quest quest) {
        questRepository.update(quest);
    }

    public Optional<Quest> getValidatedQuest(String questIdStr) {
        if (questIdStr == null || questIdStr.isEmpty()) {
            return Optional.empty();
        }
        Long questId = parseQuestIdStrToLong(questIdStr);
        return get(questId);
    }

    public Long parseQuestIdStrToLong(String questIdStr) {
        try {
            return Long.parseLong(questIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quest id");
        }
    }
}
