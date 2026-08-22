package com.javarush.aleinik.service;

import com.javarush.aleinik.cache.service.QuestStepCacheService;
import com.javarush.aleinik.exception.QuestStepCacheException;
import com.javarush.aleinik.exception.QuestStepNotFoundException;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class QuestStepService {

    private final QuestStepRepository questStepRepository;
    private final QuestStepCacheService cacheService;

    public QuestStep getQuestStepById(Long questId, Long stepId) {
        QuestStep cachedStep = findCachedStep(questId, stepId);

        if (cachedStep != null) {
            return cachedStep;
        }

        List<QuestStep> steps =
                questStepRepository.findAllByQuestIdWithChoices(questId);

        tryToCache(steps, questId);

        QuestStep questStep = findStep(steps, stepId);
        if (questStep == null) {
            throw new QuestStepNotFoundException(questId, stepId);
        }
        return questStep;
    }

    private QuestStep findStep(
            List<QuestStep> steps,
            Long stepId
    ) {
        for (QuestStep step : steps) {
            if (step.getStepId().equals(stepId)) {
                return step;
            }
        }

        return null;
    }

    private QuestStep findCachedStep(Long questId, Long stepId) {
        try {
            return cacheService.getQuestStepById(questId, stepId);
        } catch (QuestStepCacheException exception) {
            log.warn(
                    "Failed to read quest step from cache. "
                            + "Falling back to MySQL. "
                            + "questId={}, stepId={}",
                    questId,
                    stepId,
                    exception
            );

            return null;
        }
    }

    private void tryToCache(List<QuestStep> steps, Long questId) {
        try {
            cacheService.cacheAll(steps);
        } catch (QuestStepCacheException exception) {
            log.warn(
                    "Failed to cache quest steps. "
                            + "Continuing without cache. "
                            + "questId={}",
                    questId,
                    exception
            );
        }
    }
}