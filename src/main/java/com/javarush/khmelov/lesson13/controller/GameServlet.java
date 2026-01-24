package com.javarush.khmelov.lesson13.controller;

import com.javarush.khmelov.lesson13.model.GameResult;
import com.javarush.khmelov.lesson13.model.Player;
import com.javarush.khmelov.lesson13.model.Scene;
import com.javarush.khmelov.lesson13.service.GameEngine;
import com.javarush.khmelov.lesson13.service.PlayerService;
import com.javarush.khmelov.lesson13.service.QuestRegistry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    private final PlayerService playerService = PlayerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String login = (String) session.getAttribute("currentPlayerLogin");
        String questId = (String) session.getAttribute("questId");

        if (login == null || questId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Player player = playerService.findByLogin(login);
        if (player == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("player", player);

        String questPath = QuestRegistry.getPath(questId);
        GameEngine engine = new GameEngine(questPath);

        String sceneId = (String) session.getAttribute("sceneId");
        if (sceneId == null) {
            sceneId = engine.getStartSceneId();
            session.setAttribute("sceneId", sceneId);
        }

        String choiceId = req.getParameter("choice");
        if (choiceId != null) {
            GameResult result = engine.makeChoice(sceneId, choiceId);

            if (result.isGameOver()) {
                if (result.isWin()) {
                    player.recordWin();
                } else {
                    player.recordLoss();
                }
            }

            sceneId = result.getNextSceneId();
            session.setAttribute("sceneId", sceneId);

            req.setAttribute("gameOver", result.isGameOver());
            req.setAttribute("win", result.isWin());
        }

        Scene scene = engine.getScene(sceneId);
        req.setAttribute("scene", scene);

        req.getRequestDispatcher("/WEB-INF/game.jsp").forward(req, resp);
    }
}
