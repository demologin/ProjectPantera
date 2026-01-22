package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;

public class QuestPage implements Command {
    private final QuestService questService;

    public QuestPage(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String strId = req.getParameter("id");
        Quest quest = questService.getValidatedQuest(strId)
                .orElseThrow(() -> new IllegalArgumentException("Quest is not found: id=" + strId));
        req.setAttribute("quest", quest);
        return getView();
    }
}
