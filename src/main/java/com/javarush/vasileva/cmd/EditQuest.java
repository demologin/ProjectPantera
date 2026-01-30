package com.javarush.vasileva.cmd;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Link.HOME;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class EditQuest implements Command {
    private final QuestService questService;
    private final QuestMapper questMapper = Winter.find(QuestMapper.class);
    private final Config config;

    @SuppressWarnings("unused")
    public EditQuest(QuestService questService, Config config) {
        this.questService = questService;
        this.config = config;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        RequestHelpers.checkAuthorization(req, EDIT_QUEST_AUTH_ERROR);

        List<Quest> quests = questService.getAll();
        req.setAttribute(QUESTS, quests);

        String questIdStr = req.getParameter(QUEST_ID);
        if (questIdStr != null && !questIdStr.isEmpty()) {
            Quest quest = questService.findById(questIdStr)
                    .orElseThrow(() -> new AppException(QUEST_NOT_FOUND + questIdStr));
            req.setAttribute(EDIT, true);
            try {
                String questJson = questMapper.toJsonString(quest);
                req.setAttribute(QUEST_JSON, questJson);
            } catch (IOException e) {
                req.getSession().setAttribute(ERROR, QUEST_SERIALIZATION_ERROR);
                return getView();
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
            Quest quest = questMapper.fromJsonString(json);
            if (quest.getId() != null) {
                questService.update(quest);
            } else {
                questService.create(quest);
            }
            config.setQuestParameters(quest);
            return HOME;
        } catch (IOException e) {
            req.getSession().setAttribute(ERROR, JSON_SAVE_ERROR);
            return getView();
        }
    }
}
