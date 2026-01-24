package com.javarush.khmelov.lesson13.controller;

import com.javarush.khmelov.lesson13.model.Player;
import com.javarush.khmelov.lesson13.service.PlayerService;
import com.javarush.khmelov.lesson13.service.QuestRegistry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final PlayerService playerService = PlayerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("players", playerService.getAll());
        req.setAttribute("quests", QuestRegistry.getQuestIds());
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String questId = req.getParameter("questId");
        String action = req.getParameter("action"); // login / register

        try {
            Player player;

            if ("register".equals(action)) {
                player = playerService.register(login, password);
            } else {
                player = playerService.login(login, password);
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("currentPlayerLogin", player.getLogin());
            session.setAttribute("questId", questId);
            session.removeAttribute("sceneId");

            resp.sendRedirect(req.getContextPath() + "/game");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("players", playerService.getAll());
            req.setAttribute("quests", QuestRegistry.getQuestIds());
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

}