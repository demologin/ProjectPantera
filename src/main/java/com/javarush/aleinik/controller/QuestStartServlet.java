package com.javarush.aleinik.controller;


import com.javarush.aleinik.config.ApplicationConfig;
import com.javarush.aleinik.service.QuestService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/quest/start")
public class QuestStartServlet extends HttpServlet {
    QuestService questService = ApplicationConfig.getQuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String questId = req.getParameter("id");
        Long firstStepId = questService.getFirstStepId(Long.parseLong(questId));
        resp.sendRedirect(req.getContextPath() + "/quest?questId=" + questId + "&stepId=" + firstStepId);
    }
}
