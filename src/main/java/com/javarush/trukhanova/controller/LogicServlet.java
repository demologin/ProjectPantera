package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.exception.QuestException;
import com.javarush.trukhanova.service.GameLogic;
import com.javarush.trukhanova.service.TimerManager; // Наш новый сервис
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    private static final int RESPONSE_TIME_LIMIT = 20;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        TimerManager.getInstance().stopTimer(sessionId);

        GameLogic gameService = (GameLogic) getServletContext().getAttribute("gameService");

        try {
            String idParam = request.getParameter("id");
            int stepId = (idParam == null) ? 1 : Integer.parseInt(idParam);

            if (stepId == 1) {
                Player player = (Player) session.getAttribute("player");
                if (player != null) {
                    player.setGamesPlayed(player.getGamesPlayed() + 1);
                }
            }

            QuestStep currentStep = gameService.getNextStep(stepId);

            if (currentStep.getAnswers() != null && !currentStep.getAnswers().isEmpty()) {
                TimerManager.getInstance().startTimer(sessionId, RESPONSE_TIME_LIMIT, () -> {

                    System.out.println("LOG: Время вышло для игрока в сессии " + sessionId);

                    session.setAttribute("isTimeOut", true);
                });
            }

            request.setAttribute("step", currentStep);
            request.getRequestDispatcher("/game.jsp").forward(request, response);

        } catch (QuestException e) {
            request.setAttribute("error", "Проблема с квестом: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}