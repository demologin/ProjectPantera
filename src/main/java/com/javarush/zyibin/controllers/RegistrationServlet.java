package com.javarush.zyibin.controllers;


import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.service.UserService;
import com.javarush.zyibin.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(RegistrationServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET /register");

        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        log.debug("Registration attempt for username={}", username);
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        UserService userService = new UserServiceImpl(userRepository);

        try {
            User user = userService.register(username, password, email);
            log.info("User {} successfully registered", user.getUsername());
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IllegalStateException e) {
            log.warn("Registration failed for username {}: {}", username, e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        }
    }
}
