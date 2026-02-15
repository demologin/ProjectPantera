package com.javarush.trukhanova.config;

import com.javarush.trukhanova.repository.QuestRepository;
import com.javarush.trukhanova.service.GameService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        QuestRepository repository = new QuestRepository();
        GameService gameService = new GameService(repository);

        sce.getServletContext().setAttribute("gameService", gameService);
    }
}