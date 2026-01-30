package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.javarush.vasileva.util.Key.*;

public class Home implements Command {
    private final QuestService questService;

    public Home(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        try {
            List<Quest> quests = questService.getAll();
            req.setAttribute(QUESTS, quests);
            System.out.println("Quests loaded: " + quests.size());
            System.out.println("Loaded quests: " + quests);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getView();
    }

    @Override
    public String doDelete(HttpServletRequest req) {
        RequestHelpers.checkAuthorization(req, "Удалять квесты могут только пользователи с правами ADMIN");
        String questIdStr = req.getParameter(Key.QUEST_ID);
        if (questIdStr == null) {
            throw new IllegalArgumentException("Quest ID is not found");
        }

        Quest quest = questService.findById(questIdStr)
                .orElseThrow(() -> new IllegalArgumentException("Quest is not found: id=" + questIdStr));
        req.setAttribute(QUEST, quest);
        questService.delete(quest);
        return getView();
    }
}
