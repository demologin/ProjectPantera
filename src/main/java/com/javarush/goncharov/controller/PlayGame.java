package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Game;
import com.javarush.goncharov.model.Question;
import com.javarush.goncharov.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/play-game")
public class PlayGame extends DefaultServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userSession = (User) session.getAttribute("user");
        Long questId = Long.parseLong(req.getParameter("questId"));
//        Long userId = Long.parseLong(req.getParameter("userId"));
        Optional<User> user = userService.get(userSession.getId());
        if (user.isPresent()) {
            Optional<Game> game = gameService.getGame(questId, userSession.getId());
            System.out.println(game);
            if (game.isPresent()) {
                showOneQuestion(req, game.get());
                req.getRequestDispatcher("/WEB-INF/play-game.jsp").forward(req, resp);
                return;
            } else {
                String message = "Нет незавершенной игры";
                req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
                return;
            }
        } else {
            String message = "Сначала нужно войти в аккаунт";
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long gameId = Long.parseLong(req.getParameter("id"));
        Long answerId = 0L;
        String answerIdStr = req.getParameter("answer");
        if (answerIdStr != null){
            answerId = Long.parseLong(req.getParameter("answer"));
        }
        Optional<Game> game = gameService.processOneStep(gameId, answerId);
        if (game.isPresent()) {
            if (answerId == 0 && req.getParameter("game") != null) {
                String message = "Нужно выбрать какой-то ответ";
            }
            Game currentGame = game.get();
            resp.sendRedirect("/play-game?questId=%d&id=%d".formatted(game.get().getQuestId(), game.get().getId()));
            return;
        } else {
            String message = "Нет такой игры";
            resp.sendRedirect("/list-quests");
            return;
        }
    }

    private void showOneQuestion(HttpServletRequest request, Game game) {
        request.setAttribute("game", game);
        Optional<Question> question = questionService.get(game.getCurrentQuestionId());
        request.setAttribute("question", question.orElseThrow());
    }
}
