package com.javarush.vasileva.cmd;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public class UpdateQuest implements Command {
    private final QuestService questService;
    private final QuestMapper questMapper = Winter.find(QuestMapper.class);
    private final Config config;

    public UpdateQuest(QuestService questService, Config config) {
        this.questService = questService;
        this.config = config;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        List<Quest> quests = questService.getAll();
        req.setAttribute("quests", quests);

        String questIdStr = req.getParameter("questId");
        if (questIdStr != null && !questIdStr.isEmpty()) {
            Quest quest = questService.getValidatedQuest(questIdStr)
                    .orElseThrow(() -> new IllegalArgumentException("Quest is not found: id=" + questIdStr));
            req.setAttribute("quest", quest);
            try {
                String questJson = questMapper.toJsonString(quest);
                req.setAttribute("questJson", questJson);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        try {
            String json = req.getParameter("questJson");
            if (json == null || json.trim().isEmpty()) {
                req.setAttribute("error", "JSON не может быть пустым");
                return getView();
            }

            Quest quest = questMapper.fromJsonString(json);
            config.setQuestParameters(quest);
            questService.update(quest);
            return "/home";

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при сохранении квеста: " + e.getMessage());
            return getView();
        }
    }

}
