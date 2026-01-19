package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        authService = (AuthService) getServletContext().getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("view", "/WEB-INF/jsp/login.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = authService.login(login, password);

        if (user != null) {
            req.getSession().setAttribute("user", user);

            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", true);
            req.setAttribute("view", "/WEB-INF/jsp/login.jsp");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}