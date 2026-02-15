package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.exception.StepNotFoundException;
import com.javarush.trukhanova.service.GameLogic;
import com.javarush.trukhanova.service.TimerManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LogicServlet.class);
    private static final int RESPONSE_TIME_LIMIT = 20;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        TimerManager.getInstance().stopTimer(sessionId);

        GameLogic gameService = (GameLogic) getServletContext().getAttribute("gameService");
        if (gameService == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            int stepId = (idParam == null) ? 1 : Integer.parseInt(idParam);

            QuestStep currentStep = gameService.getNextStep(stepId);
            request.setAttribute("step", currentStep);

            if (gameService.isGameOver(currentStep)) {
                Player player = (Player) session.getAttribute("player");
                if (player != null) {
                    player.incrementGamesPlayed();
                    request.setAttribute("player", player);
                }
                request.getRequestDispatcher("/WEB-INF/jsp/final.jsp").forward(request, response);
                return;
            }

            TimerManager.getInstance().startTimer(sessionId, RESPONSE_TIME_LIMIT, () -> {
                logger.warn("Время вышло для сессии: {}", sessionId);
                session.setAttribute("isTimeOut", true);
            });

            request.getRequestDispatcher("/WEB-INF/jsp/game.jsp").forward(request, response);

        } catch (StepNotFoundException e) {
            response.sendRedirect("logic?id=1");
        } catch (Exception e) {
            logger.error("Ошибка: ", e);
            response.sendRedirect("index.jsp");
        }
    }
}