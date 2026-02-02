package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.repository.QuestRepository;
import com.javarush.vasileva.repository.Repository;
import com.javarush.vasileva.util.Helpers;

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

    public Optional<Quest> findById(Long questId) {
        return questRepository.findById(questId);
    }

    public void create(Quest quest) {
        questRepository.create(quest);
    }

    public void update(Quest quest) {
        questRepository.update(quest);
    }

    public void delete(Quest quest) {
        questRepository.delete(quest);
    }

    public Optional<Quest> getValidatedQuest(String questIdStr) {
        if (questIdStr == null || questIdStr.isEmpty()) {
            return Optional.empty();
        }
        Long questId = Helpers.parseStringToLong(questIdStr);
        return findById(questId);
    }
}
