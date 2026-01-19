package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.model.QuestSession;
import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.service.QuestService;
import com.javarush.golikov.quest.service.StatisticsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/exitQuest")
public class ExitQuestController extends HttpServlet {

    private QuestService questService;
    private StatisticsService statisticsService;

    @Override
    public void init() {
        questService = (QuestService) getServletContext().getAttribute("questService");
        statisticsService = (StatisticsService) getServletContext().getAttribute("statisticsService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        QuestSession qs = (QuestSession) req.getSession().getAttribute("quest");
        User user = (User) req.getSession().getAttribute("user");

        if (qs != null) {

            String login = (user != null) ? user.login() : "Гость";
            String questId = qs.getQuestId();

            statisticsService.saveResult(login, questId, false);

            questService.exitQuest(qs);
            req.getSession().removeAttribute("quest");

            req.setAttribute("result", "lose");
        }

        req.setAttribute("view", "/WEB-INF/jsp/result.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}