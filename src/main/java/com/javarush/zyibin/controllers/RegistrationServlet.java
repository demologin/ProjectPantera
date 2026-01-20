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

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        UserService userService = new UserServiceImpl(userRepository);

        try {
            User user = userService.register(username, password, email);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        }
    }
}
