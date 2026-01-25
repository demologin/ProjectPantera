package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.MessageRepository;
import com.javarush.goncharov.repository.UserRepository;
import com.javarush.goncharov.service.MessageService;
import com.javarush.goncharov.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        UserService userService = UserService.getInstance(UserRepository.getInstance());
        Optional<User> user = userService.find(login);
        if (user.isPresent()){
            HttpSession session = req.getSession();
            session.setAttribute("user", user.get());
            resp.sendRedirect("/profile");
        } else {
            resp.sendRedirect("/login");
        }
    }
}
