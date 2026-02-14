package com.javarush.goncharov.controller;

import com.javarush.goncharov.repository.*;
import com.javarush.goncharov.service.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {
    final Storage storage = Storage.getInstance();
    final UserService userService;
    final MessageService messageService;
    final QuestService questService;
    final QuestionService questionService;
    final GameService gameService;
    final AnswerRepository answerRepository;
    final StatisticService statisticService;

    public AppConfig() {
        log.info("Init MessageService..");
        messageService = new MessageService(new MessageRepository(storage));
        log.info("Init UserService..");
        userService = new UserService(new UserRepository(storage));
        log.info("Init QuestionService..");
        questionService = new QuestionService(new QuestionRepository(storage));
        log.info("Init QuestionService..");
        answerRepository = new AnswerRepository(storage);
        log.info("Init QuestService..");
        questService = new QuestService(answerRepository,
                questionService,
                userService,
                new QuestRepository(storage));
        log.info("Init GameService..");
        gameService = new GameService(new GameRepository(storage),
                questService,
                questionService,
                userService,
                answerRepository);
        log.info("Init StatisticService..");
        statisticService = new StatisticService(new GameRepository(storage),
                new UserRepository(storage));
        
    }
}
