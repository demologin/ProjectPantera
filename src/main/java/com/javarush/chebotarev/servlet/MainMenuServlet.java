package com.javarush.chebotarev.servlet;

import com.javarush.chebotarev.component.*;
import com.javarush.chebotarev.quest.QuestMetadata;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(Go.MAIN_MENU)
public class MainMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        QuestService questService = ObjectRepository.getQuestService();
        List<QuestMetadata> availableQuests = questService.obtainAvailableQuests(getServletContext());
        currentSession.setAttribute("availableQuests", availableQuests);
        Statistics statistics = Utils.tryExtractAttribute(
                currentSession,
                "statistics",
                Statistics.class
        );
        if (statistics == null) {
            statistics = new Statistics();
            currentSession.setAttribute("statistics", statistics);
        }
        req.getRequestDispatcher(Path.MAIN_MENU)
                .forward(req, resp);
    }
}
