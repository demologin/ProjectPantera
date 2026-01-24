package com.javarush.zyibin.config;

import com.javarush.zyibin.repository.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppInitListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(AppInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Application context initialization started");

        ServletContext context = sce.getServletContext();

        UserRepository userRepository = new InMemoryUserRepository();
        context.setAttribute("userRepository", userRepository);
        log.info("UserRepository initialized");

        TestResultRepository testResultRepository = new InMemoryTestResultRepository();
        context.setAttribute("testResultRepository", testResultRepository);
        log.info("TestResultRepository initialized");

        QuestionRepository questionRepository =
                QuestionRepository.defaultRepository();
        context.setAttribute("questionRepository", questionRepository);
        log.info("QuestionRepository initialized");

        log.info("Application context initialization completed successfully");
    }
}
