package com.javarush.chebotarev.servlet;

import com.javarush.chebotarev.component.*;
import com.javarush.chebotarev.quest.CurrentQuest;
import com.javarush.chebotarev.quest.Quest;
import com.javarush.chebotarev.quest.QuestMetadata;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(Go.NEW_QUEST)
public class NewQuestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        int selectedQuestIndex = getSelectedQuestIndex(req);
        List<QuestMetadata> availableQuests = Utils.extractAttribute(
                currentSession,
                "availableQuests",
                ArrayList.class
        );
        QuestService questService = ObjectRepository.getQuestService();
        Quest quest = questService.loadQuest(
                availableQuests.get(selectedQuestIndex),
                getServletContext()
        );
        CurrentQuest currentQuest = new CurrentQuest(quest);
        currentSession.setAttribute("currentQuest", currentQuest);
        req.getRequestDispatcher(Path.NEW_QUEST)
                .forward(req, resp);
    }

    private int getSelectedQuestIndex(HttpServletRequest req) {
        String questIndexString = req.getParameter("questIndex");
        return Integer.parseInt(questIndexString);
    }
}
