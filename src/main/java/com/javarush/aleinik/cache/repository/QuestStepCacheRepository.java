package com.javarush.aleinik.cache.repository;
import com.javarush.aleinik.cache.dto.QuestStepCacheDto;

import java.util.List;

public interface QuestStepCacheRepository {
    QuestStepCacheDto findStepByQuestId(Long questId, Long stepId);

    void save(QuestStepCacheDto step);

    void saveAll(List<QuestStepCacheDto> steps);

    void deleteStep(Long questId, Long stepId);
}
