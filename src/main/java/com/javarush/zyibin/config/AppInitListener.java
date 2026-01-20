package com.javarush.zyibin.config;

import com.javarush.zyibin.repository.InMemoryUserRepository;
import com.javarush.zyibin.repository.UserRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserRepository userRepository = new InMemoryUserRepository();
        context.setAttribute("userRepository", userRepository);
    }
}
