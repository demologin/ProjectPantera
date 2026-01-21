package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class QuestPage implements Command {
    private final QuestService questService;

    public QuestPage(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String strId = req.getParameter("id");
        if (strId != null && !strId.isEmpty()) {
            long id = Long.parseLong(strId);
            Quest quest = questService.get(id).orElseThrow();
            req.setAttribute("quest", quest);
        }
        return getView();
    }
}
