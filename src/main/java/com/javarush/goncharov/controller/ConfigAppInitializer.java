package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Role;
import com.javarush.goncharov.model.Topic;
import com.javarush.goncharov.service.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ConfigAppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppConfig appConfig = new AppConfig();

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("userService", appConfig.getUserService());
        servletContext.setAttribute("messageService", appConfig.getMessageService());
        servletContext.setAttribute("questService", appConfig.getQuestService());
        servletContext.setAttribute("questionService", appConfig.getQuestionService());
        servletContext.setAttribute("gameService", appConfig.getGameService());
        servletContext.setAttribute("answerRepository", appConfig.getAnswerRepository());
        servletContext.setAttribute("statisticService", appConfig.getStatisticService());
        servletContext.setAttribute("topics", Topic.values());
        servletContext.setAttribute("roles", Role.values());
    }
}
