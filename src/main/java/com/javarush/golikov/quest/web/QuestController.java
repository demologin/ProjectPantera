package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.model.QuestNode;
import com.javarush.golikov.quest.model.QuestSession;
import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.service.QuestService;
import com.javarush.golikov.quest.service.StatisticsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/play")
public class QuestController extends HttpServlet {

    private QuestService questService;
    private StatisticsService statisticsService;

    @Override
    public void init() {
        questService = (QuestService) getServletContext().getAttribute("questService");
        statisticsService = (StatisticsService) getServletContext().getAttribute("statisticsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        QuestSession qs = (QuestSession) req.getSession().getAttribute("quest");

        if (qs == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        QuestNode node = questService.getCurrentNode(qs);

        // ===== ФИНАЛ КВЕСТА =====
        if (questService.isFinalNode(node)) {

            boolean win = questService.isWinNode(node);

            if (win) {
                qs.win();
                req.setAttribute("result", "win");
            } else {
                qs.lose();
                req.setAttribute("result", "lose");
            }

            User user = (User) req.getSession().getAttribute("user");
            String login = (user == null) ? "Гость" : user.login();

            statisticsService.saveResult(
                    login,
                    qs.getQuestId(),
                    win
            );

            req.getSession().removeAttribute("quest");

            req.setAttribute("view", "/WEB-INF/jsp/result.jsp");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        // ===== ОБЫЧНЫЙ ШАГ КВЕСТА =====
        req.setAttribute("node", node);
        req.setAttribute("questSession", qs);
        req.setAttribute("questTitle", questService.getQuestTitle(qs));

        req.setAttribute("view", "/WEB-INF/jsp/quest-view.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        QuestSession qs = (QuestSession) req.getSession().getAttribute("quest");
        if (qs == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String next = req.getParameter("next");
        String answer = req.getParameter("answer");

        questService.applyChoice(qs, next, answer);

        resp.sendRedirect(req.getContextPath() +"/play");
    }
}