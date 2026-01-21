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

    public void create(Quest entity) {
        questRepository.create(entity);
    }
}
