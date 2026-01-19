package com.javarush.khmelov.lesson13.controller;

import com.javarush.khmelov.lesson13.model.GameResult;
import com.javarush.khmelov.lesson13.model.Scene;
import com.javarush.khmelov.lesson13.service.GameEngine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    private final GameEngine engine = new GameEngine();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String sceneId = (String) session.getAttribute("sceneId");
        if (sceneId == null) {
            sceneId = "start";
            session.setAttribute("sceneId", sceneId);
        }

        String choiceId = req.getParameter("choice");
        if (choiceId != null) {
            GameResult result = engine.makeChoice(sceneId, choiceId);
            sceneId = result.getNextSceneId();
            session.setAttribute("sceneId", sceneId);

            req.setAttribute("gameOver", result.isGameOver());
            req.setAttribute("win", result.isWin());
        }

        Scene scene = engine.getScene(sceneId);
        req.setAttribute("scene", scene);

        req.getRequestDispatcher("/WEB-INF/game.jsp")
                .forward(req, resp);
    }
}
