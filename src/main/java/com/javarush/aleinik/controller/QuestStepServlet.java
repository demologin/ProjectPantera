package com.javarush.aleinik.controller;


import com.javarush.aleinik.config.ApplicationConfig;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.model.enums.QuestStepResult;
import com.javarush.aleinik.service.QuestStepService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.*;

@WebServlet("/quest")
public class QuestStepServlet extends HttpServlet {
    QuestStepService questStepService = ApplicationConfig.getQuestStepService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long questId = Long.parseLong(req.getParameter("questId"));
        Long stepId = Long.parseLong(req.getParameter("stepId"));

        HttpSession session = req.getSession();
        Map<Long, Long> progressByQuestId = Optional
                .ofNullable((Map<Long, Long>) session.getAttribute("progressByQuestId"))
                .orElse(new HashMap<>());

        Set<Long> completedQuestIds = Optional
                .ofNullable((Set<Long>) session.getAttribute("completedQuestIds"))
                .orElse(new HashSet<>());

        QuestStep step = questStepService.getQuestStepById(questId, stepId);
        switch (step.getResult()) {
            case CONTINUE -> progressByQuestId.put(questId, stepId);
            case LOSE -> progressByQuestId.remove(questId);
            case WIN -> {
                progressByQuestId.remove(questId);
                completedQuestIds.add(questId);
            }
        }
        session.setAttribute("progressByQuestId", progressByQuestId);
        session.setAttribute("completedQuestIds", completedQuestIds);

        req.setAttribute("step", step);
        req.setAttribute("progressByQuestId", progressByQuestId);
        req.setAttribute("completedQuestIds", completedQuestIds);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/quest-step.jsp");
        requestDispatcher.forward(req, resp);

    }
}
