package com.javarush.aleinik.service;


import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuestStepService {
    QuestStepRepository questStepRepository;

    public QuestStep getQuestStepById(Long questId, Long stepId){
        return questStepRepository.findStepByQuestId(questId, stepId);
    }
}
