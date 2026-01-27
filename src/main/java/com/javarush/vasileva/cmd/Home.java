package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.javarush.vasileva.util.Key.QUESTS;

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
}
