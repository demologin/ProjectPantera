package com.javarush.aleinik.repository;

import com.javarush.aleinik.model.QuestStep;

import java.util.List;

public interface QuestStepRepository extends Repository<QuestStep, Long>{
    QuestStep findStepByQuestId(Long questId, Long stepId);

    List<QuestStep> findAllByQuestIdWithChoices(Long questId);
}
