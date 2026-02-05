package com.javarush.goncharov.controller;

import com.javarush.goncharov.repository.*;
import com.javarush.goncharov.service.*;
import lombok.Getter;

@Getter
public class AppConfig {
    private final Storage storage = Storage.getInstance();
    private final UserService userService;
    private final MessageService messageService;
    private final QuestService questService;
    private final QuestionService questionService;
    private final GameService gameService;
    private final AnswerRepository answerRepository;

    public AppConfig() {
        this.messageService = new MessageService(new MessageRepository(storage));
        this.userService = new UserService(new UserRepository(storage));
        this.questService = new QuestService(new QuestRepository(storage));
        this.questionService = new QuestionService(new QuestionRepository(storage));
        this.gameService = new GameService(new GameRepository(storage));
        this.answerRepository = new AnswerRepository(storage);
    }
}
