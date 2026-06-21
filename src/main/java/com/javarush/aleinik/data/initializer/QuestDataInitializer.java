package com.javarush.aleinik.data.initializer;

import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestRepository;
import com.javarush.aleinik.repository.QuestStepRepository;

import java.util.List;

public class QuestDataInitializer {

    private final QuestDataLoader questDataLoader;
    private final QuestStepRepository questStepRepository;
    private final QuestRepository questRepository;

    public QuestDataInitializer(QuestDataLoader questDataLoader, QuestRepository questRepository, QuestStepRepository questStepRepository) {
        this.questRepository = questRepository;
        this.questStepRepository = questStepRepository;
        this.questDataLoader = questDataLoader;
    }

    public void initialize(String questName) {
        QuestDefinition quest = questDataLoader.load(questName);
        Quest savedQuest = createQuest(quest.getTitle(), quest.getDescription(), quest.getFirstStepId());
        createQuestSteps(savedQuest.getId(), quest.getSteps());

    }

    private Quest createQuest(String title, String description, Long firstStepId){
        Quest quest = Quest.builder()
                .id(null)
                .title(title)
                .description(description)
                .firstStepId(firstStepId)
                .build();
        return questRepository.save(quest);
    }

    private void createQuestSteps(Long questId, List<QuestStep> steps){
        steps.forEach(questStep -> {

            questStep.setQuestId(questId);
            questStepRepository.save(questStep);

        });

    }


}
