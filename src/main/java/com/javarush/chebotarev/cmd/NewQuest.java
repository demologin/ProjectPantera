package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.component.*;
import com.javarush.chebotarev.quest.CurrentQuest;
import com.javarush.chebotarev.quest.Quest;
import com.javarush.chebotarev.quest.QuestMetadata;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class NewQuest extends Command {

    @Override
    @SuppressWarnings("unchecked")
    public String doGet(HttpServletRequest req, HttpServlet servlet) {
        HttpSession currentSession = req.getSession();
        int selectedQuestIndex = getSelectedQuestIndex(req);
        List<QuestMetadata> availableQuests = Utils.extractAttribute(
                currentSession,
                Attribute.AVAILABLE_QUESTS,
                ArrayList.class
        );
        QuestService questService = ObjectRepository.find(QuestService.class);
        QuestMetadata selectedQuest = availableQuests.get(selectedQuestIndex);
        Quest quest = questService.loadQuest(
                selectedQuest,
                servlet.getServletContext()
        );
        CurrentQuest currentQuest = new CurrentQuest(quest);
        currentSession.setAttribute(Attribute.CURRENT_QUEST, currentQuest);
        return getView();
    }

    private int getSelectedQuestIndex(HttpServletRequest req) {
        String questIndexString = req.getParameter(Parameter.QUEST_INDEX);
        return Integer.parseInt(questIndexString);
    }
}
