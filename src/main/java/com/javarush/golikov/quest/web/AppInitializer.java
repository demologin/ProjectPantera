package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.service.AuthService;
import com.javarush.golikov.quest.service.QuestService;
import com.javarush.golikov.quest.service.AdminService;

import com.javarush.golikov.quest.service.StatisticsService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("authService", new AuthService());
        sce.getServletContext().setAttribute("questService", new QuestService());
        sce.getServletContext().setAttribute("adminService", new AdminService());
        sce.getServletContext().setAttribute("statisticsService", new StatisticsService());
    }
}