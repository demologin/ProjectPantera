package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.service.GameService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameService gameService = (GameService) getServletContext().getAttribute("gameService");

        String idParam = request.getParameter("id");
        int stepId = (idParam == null) ? 1 : Integer.parseInt(idParam);

        if (stepId == 1) {
            Player player = (Player) session.getAttribute("player");
            if (player != null) {
                int currentGames = player.getGamesPlayed();
                player.setGamesPlayed(currentGames + 1);
                session.setAttribute("player", player);
            }
        }

        // 3. Получаем данные шага
        QuestStep currentStep = gameService.getNextStep(stepId);

        request.setAttribute("step", currentStep);
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }
}