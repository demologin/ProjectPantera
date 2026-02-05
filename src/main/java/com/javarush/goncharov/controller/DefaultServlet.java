package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Topic;
import com.javarush.goncharov.repository.AnswerRepository;
import com.javarush.goncharov.service.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import lombok.Getter;

@Getter
public class DefaultServlet extends HttpServlet {
    protected UserService userService;
    protected MessageService messageService;
    protected QuestService questService;
    protected QuestionService questionService;
    protected GameService gameService;
    protected AnswerRepository answerRepository;
    protected Topic topics;

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
    }
}
