package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.repository.QuestRepository;
import com.javarush.vasileva.repository.Repository;
import com.javarush.vasileva.util.RequestHelpers;

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

    public Optional<Quest> findById(String questIdStr) {
        return getValidatedQuest(questIdStr);
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

    private Optional<Quest> getValidatedQuest(String questIdStr) {
        if (questIdStr == null || questIdStr.isEmpty()) {
            return Optional.empty();
        }
        Long questId = RequestHelpers.parseStringToLong(questIdStr);
        return questRepository.findById(questId);
    }
}
