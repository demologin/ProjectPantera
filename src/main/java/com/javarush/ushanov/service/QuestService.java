package com.javarush.ushanov.service;

import com.javarush.ushanov.entity.QuestStep;
import com.javarush.ushanov.exception.QuestStepNotFoundException;
import com.javarush.ushanov.repository.QuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestService {

    private static final Logger log = LoggerFactory.getLogger(QuestService.class);

    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public QuestStep getStep(int stepId) {
        return questRepository.findById(stepId)
                .orElseThrow(() -> {
                    // ERROR — что-то пошло не так, нужно разобраться
                    log.error("Quest step with id={} not found", stepId);
                    return new QuestStepNotFoundException("Quest step with id=" + stepId + " not found");
                });
    }

    public int getStartStepId() {
        return questRepository.getStartStepId();
    }

    public int getNextStepId(int currentStepId, String chosenOption) {
        QuestStep currentStep = getStep(currentStepId);
        Integer nextStepId = currentStep.getOptions().get(chosenOption);
        if (nextStepId == null) {
            log.error("Choice option '{}' not found for step id={}", chosenOption, currentStepId);
            throw new QuestStepNotFoundException(
                    "Choice option '" + chosenOption + "' not found for step id=" + currentStepId
            );
        }
        log.debug("Transition: step {} -> step {} (choice: '{}')", currentStepId, nextStepId, chosenOption);
        return nextStepId;
    }
}
