package com.javarush.aleinik.cache.repository.redis_chache_repo_impl;

import com.javarush.aleinik.cache.dto.QuestStepCacheDto;
import com.javarush.aleinik.cache.repository.QuestStepCacheRepository;

import java.util.List;

public class NoOpQuestStepCacheRepository implements QuestStepCacheRepository {
    @Override
    public QuestStepCacheDto findStepByQuestId(Long questId, Long stepId) {
        return null;
    }

    @Override
    public void save(QuestStepCacheDto step) {

    }

    @Override
    public void saveAll(List<QuestStepCacheDto> steps) {

    }

    @Override
    public void deleteStep(Long questId, Long stepId) {

    }
}
