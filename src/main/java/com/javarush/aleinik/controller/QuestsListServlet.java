package com.javarush.aleinik.controller;

import com.javarush.aleinik.config.ApplicationConfig;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.service.QuestService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;


@WebServlet("/quests")
public class QuestsListServlet extends HttpServlet {
    QuestService questService = ApplicationConfig.getQuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        Map<Long, Long> progressByQuestId = Collections.emptyMap();
        Set<Long> completedQuestIds  = Collections.emptySet();

        if(session != null){

            progressByQuestId = Optional
                    .ofNullable((Map<Long, Long>) session.getAttribute("progressByQuestId"))
                    .orElse(Collections.emptyMap());

            completedQuestIds = Optional
                    .ofNullable((Set<Long>) session.getAttribute("completedQuestIds"))
                    .orElse(Collections.emptySet());
        }

        req.setAttribute("progressByQuestId", progressByQuestId);
        req.setAttribute("completedQuestIds", completedQuestIds);

        List<Quest> availableQuests = questService.getAllQuests();
        req.setAttribute("quests", availableQuests);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/list-quests.jsp");
        requestDispatcher.forward(req, resp);

    }
}
