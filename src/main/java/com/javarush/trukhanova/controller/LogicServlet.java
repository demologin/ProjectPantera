package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.exception.QuestException;
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
            logger.error("GameService не найден в контексте сервлета");
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            int stepId = (idParam == null) ? 1 : Integer.parseInt(idParam);

            logger.info("Игрок (Сессия: {}) перешел на шаг ID: {}", sessionId, stepId);

            Player player = (Player) session.getAttribute("player");

            if (player != null && stepId == 7) {
                player.incrementGamesPlayed();
                logger.info("Игрок {} достиг финала. Побед: {}", player.getName(), player.getGamesPlayed());
            }

            QuestStep currentStep = gameService.getNextStep(stepId);

            if (currentStep.getAnswers() != null && !currentStep.getAnswers().isEmpty()) {
                TimerManager.getInstance().startTimer(sessionId, RESPONSE_TIME_LIMIT, () -> {
                    logger.warn("Время вышло для сессии: {}", sessionId);
                    session.setAttribute("isTimeOut", true);
                });
            }

            request.setAttribute("step", currentStep);
            request.getRequestDispatcher("/game.jsp").forward(request, response);

        } catch (StepNotFoundException e) {
            logger.error("Шаг не найден: {}", e.getMessage());
            response.sendRedirect("logic?id=1");
        } catch (QuestException e) {
            logger.error("Критическая ошибка квеста: {}", e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Непредвиденная ошибка: ", e);
            response.sendRedirect("index.jsp");
        }
    }
}