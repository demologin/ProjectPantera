package com.javarush.aleinik.service;

import com.javarush.aleinik.exception.QuestNotFoundException;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.repository.QuestRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QuestService {

    QuestRepository questRepository;


    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }


    public Long getFirstStepId(Long id) {
        Quest currentQuest = questRepository.findById(id);
        if (currentQuest == null) {
            throw new QuestNotFoundException(id);
        }
        return currentQuest.getFirstStepId();
    }

}
