package com.javarush.aleinik.data.initializer;

import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.repository.QuestRepository;
import com.javarush.aleinik.repository.QuestStepRepository;

import java.util.ArrayList;


public class QuestDataInitializer {

    private final QuestDataLoader questDataLoader;
    private final QuestRepository questRepository;

    public QuestDataInitializer(QuestDataLoader questDataLoader, QuestRepository questRepository, QuestStepRepository questStepRepository) {
        this.questRepository = questRepository;
        this.questDataLoader = questDataLoader;
    }

    public void initialize(String questName) {
        QuestDefinition definition = questDataLoader.load(questName);
        Quest quest = Quest.builder()
                .title(definition.getTitle())
                .description(definition.getDescription())
                .firstStepId(definition.getFirstStepId())
                .build();

        definition.getSteps().forEach(step ->{
            if (step.getChoices() == null) {
                step.setChoices(new ArrayList<>());
            }
            step.getChoices().forEach(
                    choice -> choice.setQuestStep(step)
            );
            quest.addStep(step);
        });

        questRepository.save(quest);

    }

}
