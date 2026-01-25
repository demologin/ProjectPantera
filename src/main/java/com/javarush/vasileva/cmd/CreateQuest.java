package com.javarush.vasileva.cmd;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;

public class CreateQuest implements Command{

    private final QuestService questService;
    private final Config config;
    private final QuestMapper objectMapper = Winter.find(QuestMapper.class);

    public CreateQuest(QuestService questService, Config config) {
        this.questService = questService;
        this.config = config;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        try {
            String json = req.getParameter("questJson");
            if (json == null || json.trim().isEmpty()) {
                req.setAttribute("error", "JSON не может быть пустым");
                return getView();
            }

            Quest quest = objectMapper.fromJsonString(json);
            questService.create(quest);
            config.setQuestParameters(quest);
            return "/home";

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при сохранении квеста: " + e.getMessage());
            return getView();
        }
    }
}
