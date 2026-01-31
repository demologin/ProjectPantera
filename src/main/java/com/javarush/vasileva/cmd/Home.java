package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class Home implements Command {
    private final QuestService questService;

    public Home(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        List<Quest> quests = questService.getAll();
        req.setAttribute(QUESTS, quests);
        return getView();
    }

    @Override
    public String doDelete(HttpServletRequest req) {
        Helpers.checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);
        String questIdStr = req.getParameter(QUEST_ID);
        Quest quest = questService.findById(questIdStr)
                .orElseThrow(() -> new IllegalArgumentException(QUEST_NOT_FOUND + questIdStr));
        req.setAttribute(QUEST, quest);
        questService.delete(quest);
        return getView();
    }
}
