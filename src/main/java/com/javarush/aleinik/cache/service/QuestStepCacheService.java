package com.javarush.aleinik.cache.service;

import com.javarush.aleinik.cache.dto.QuestStepCacheDto;
import com.javarush.aleinik.cache.mapper.QuestStepCacheMapper;
import com.javarush.aleinik.cache.repository.QuestStepCacheRepository;
import com.javarush.aleinik.model.QuestStep;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QuestStepCacheService {

    private final QuestStepCacheRepository questStepCacheRepository;
    private final QuestStepCacheMapper mapper;

    public QuestStep getQuestStepById(
            Long questId,
            Long stepId
    ) {
        QuestStepCacheDto cacheDto =
                questStepCacheRepository.findStepByQuestId(
                        questId,
                        stepId
                );

        if (cacheDto == null) {
            return null;
        }

        return mapper.fromCacheDto(cacheDto);
    }

    public void cacheAll(List<QuestStep> steps) {
        List<QuestStepCacheDto> cacheDtos = steps.stream()
                .map(mapper::toCacheDto)
                .toList();

        questStepCacheRepository.saveAll(cacheDtos);
    }
}