package com.javarush.aleinik.config;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ApplicationLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Project Pantera initialization started");

        ApplicationConfig.initialize();

        log.info("Project Pantera initialization completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log.info("Project Pantera shutdown started");

        ApplicationConfig.shutdown();

        log.info("Project Pantera shutdown completed");
    }

}
