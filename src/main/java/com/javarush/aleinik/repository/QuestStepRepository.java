package com.javarush.aleinik.repository;

import com.javarush.aleinik.model.QuestStep;

public interface QuestStepRepository extends Repository<QuestStep, Long>{
    QuestStep findStepByQuestId(Long questId, Long stepId);
}
