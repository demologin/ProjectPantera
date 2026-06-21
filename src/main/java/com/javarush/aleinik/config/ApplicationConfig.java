package com.javarush.aleinik.config;

import com.javarush.aleinik.data.initializer.QuestDataInitializer;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.repository.QuestRepository;
import com.javarush.aleinik.repository.QuestStepRepository;
import com.javarush.aleinik.repository.impl.InMemoryQuestRepository;
import com.javarush.aleinik.repository.impl.InMemoryQuestStepRepository;
import com.javarush.aleinik.service.QuestService;
import com.javarush.aleinik.service.QuestStepService;
import lombok.Getter;

public class ApplicationConfig {
    private static final QuestRepository questRepository = new InMemoryQuestRepository();
    private static final QuestStepRepository questStepRepository = new InMemoryQuestStepRepository();


    @Getter
    private static final QuestService questService = new QuestService(questRepository);

    @Getter
    private static final QuestStepService questStepService = new QuestStepService(questStepRepository);


    private static final QuestDataLoader questDataLoader = new QuestDataLoader();

    private static final QuestDataInitializer questDataInitializer =
            new QuestDataInitializer(questDataLoader, questRepository, questStepRepository);

    static {
        questDataInitializer.initialize("pantera");
        questDataInitializer.initialize("matrix");
    }

}
