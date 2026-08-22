package com.javarush.aleinik.repository.in_memory_repo_impl;

import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryQuestStepRepository implements QuestStepRepository {
    Map<Long, Map<Long, QuestStep>> stepsByQuestId;

    public InMemoryQuestStepRepository() {

        stepsByQuestId = new ConcurrentHashMap<>();
    }

    @Override
    public List<QuestStep> findAll() {
        return List.of();
    }

    @Override
    public QuestStep findById(Long aLong) {
        return null;
    }

    @Override
    public QuestStep save(QuestStep entity) {
        stepsByQuestId
                .computeIfAbsent(entity.getQuest().getId(), k -> new ConcurrentHashMap<>())
                .put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Long aLong) {
    }

    @Override
    public QuestStep findStepByQuestId(Long questId, Long stepId) {
        Map<Long, QuestStep> steps = stepsByQuestId.get(questId);
        if (steps == null) return null;
        return steps.get(stepId);
    }

    @Override
    public List<QuestStep> findAllByQuestIdWithChoices(Long questId) {
        return List.of();
    }

}
