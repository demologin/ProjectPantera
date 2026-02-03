package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.component.*;
import com.javarush.chebotarev.quest.QuestMetadata;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@SuppressWarnings("unused")
public class MainMenu extends Command {

    @Override
    public String doGet(HttpServletRequest req, HttpServlet servlet) {
        HttpSession currentSession = req.getSession();
        QuestService questService = ObjectRepository.find(QuestService.class);
        List<QuestMetadata> availableQuests
                = questService.obtainAvailableQuests(servlet.getServletContext());
        currentSession.setAttribute(Attribute.AVAILABLE_QUESTS, availableQuests);
        Statistics statistics = Utils.tryExtractAttribute(
                currentSession,
                Attribute.STATISTICS,
                Statistics.class
        );
        if (statistics == null) {
            statistics = new Statistics();
            currentSession.setAttribute(Attribute.STATISTICS, statistics);
        }
        return getView();
    }
}
