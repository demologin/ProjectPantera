package com.javarush.vasileva.cmd;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Link.HOME;
import static com.javarush.vasileva.util.Value.*;

public class EditQuest implements Command {
    private final QuestService questService;
    private final QuestMapper questMapper = Winter.find(QuestMapper.class);
    private final Config config;

    public EditQuest(QuestService questService, Config config) {
        this.questService = questService;
        this.config = config;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        List<Quest> quests = questService.getAll();
        req.setAttribute(QUESTS, quests);

        String questIdStr = req.getParameter(QUEST_ID);
        if (questIdStr != null && !questIdStr.isEmpty()) {
            Quest quest = questService.getValidatedQuest(questIdStr)
                    .orElseThrow(() -> new IllegalArgumentException("Quest is not found: id=" + questIdStr));
            req.setAttribute(EDIT, true);
            try {
                String questJson = questMapper.toJsonString(quest);
                req.setAttribute(QUEST_JSON, questJson);
            } catch (IOException e) {
                throw new RuntimeException("Quest serialization error", e);
            }
        } else {
            req.setAttribute(EDIT, false);
            req.setAttribute(QUEST_JSON, JSON_SAMPLE);
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        try {
            String json = req.getParameter(QUEST_JSON);
            if (json == null || json.trim().isEmpty()) {
                req.setAttribute(ERROR, JSON_EMPTY_ERROR);
                return getView();
            }

            Quest quest = questMapper.fromJsonString(json);
            if (quest.getId() != null) {
                questService.update(quest);
            } else {
                questService.create(quest);
            }
            config.setQuestParameters(quest);
            req.setAttribute(INFO, QUEST_SUCCESS);
            return HOME;

        } catch (Exception e) {
            req.setAttribute(ERROR, JSON_SAVE_ERROR + ": " + e.getMessage());
            return getView();
        }
    }
}
