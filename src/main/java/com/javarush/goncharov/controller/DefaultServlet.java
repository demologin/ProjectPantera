package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Topic;
import com.javarush.goncharov.repository.AnswerRepository;
import com.javarush.goncharov.service.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class DefaultServlet extends HttpServlet {
    UserService userService;
    MessageService messageService;
    QuestService questService;
    QuestionService questionService;
    GameService gameService;
    AnswerRepository answerRepository;
    StatisticService statisticService;
    Topic topics;

    @Override
    public void init() throws ServletException {
        super.init();
        initializeSpecificServices();
    }

    protected void initializeSpecificServices(){
        ServletContext servletContext = getServletContext();
        messageService = (MessageService) servletContext.getAttribute("messageService");
        userService = (UserService) servletContext.getAttribute("userService");
        questService = (QuestService) servletContext.getAttribute("questService");
        questionService = (QuestionService) servletContext.getAttribute("questionService");
        gameService = (GameService) servletContext.getAttribute("gameService");
        answerRepository = (AnswerRepository) servletContext.getAttribute("answerRepository");
        statisticService = (StatisticService) servletContext.getAttribute("statisticService");
    }
}
