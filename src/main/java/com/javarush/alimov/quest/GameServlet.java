package com.javarush.alimov.quest;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.*;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        GameState state = (GameState) session.getAttribute("state");
        if (state == null) state = GameState.START;

        String answer = req.getParameter("answer");
        GameResult result = GameLogic.next(state, answer);

        session.setAttribute("state", result.state());
        session.setAttribute("message", result.message());

        if (result.state() == GameState.WIN || result.state() == GameState.LOSE) {
            Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
            session.setAttribute("gamesPlayed", gamesPlayed == null ? 1 : gamesPlayed + 1);
            req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/game.jsp").forward(req, resp);
        }
    }
}



