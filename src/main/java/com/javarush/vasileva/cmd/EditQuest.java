package com.javarush.vasileva.cmd;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.AuthService;
import com.javarush.vasileva.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Link.HOME;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class EditQuest implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditQuest.class.getName());

    private final QuestService questService;
    private final AuthService authService;
    private final QuestMapper questMapper = Winter.find(QuestMapper.class);
    private final Config config;

    @SuppressWarnings("unused")
    public EditQuest(QuestService questService, AuthService authService, Config config) {
        this.questService = questService;
        this.authService = authService;
        this.config = config;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        LOGGER.info("Received GET request to edit quests");

        authService.checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);

        List<Quest> quests = questService.getAll();
        req.setAttribute(QUESTS, quests);
        LOGGER.debug("Loaded quests: {}", quests.size());

        String questIdStr = req.getParameter(QUEST_ID);
        if (questIdStr != null && !questIdStr.isEmpty()) {
            LOGGER.info("Editing quest with id: {}", questIdStr);
            Quest quest = questService.getValidatedQuest(questIdStr)
                    .orElseThrow(() -> new AppException(QUEST_NOT_FOUND + questIdStr));
            req.setAttribute(EDIT, true);
            try {
                String questJson = questMapper.toJsonString(quest);
                System.out.println("json" + questJson);
                req.setAttribute(QUEST_JSON, questJson);
                LOGGER.debug("Quest is mapped to JSON. ID: {}", quest.getId());
            } catch (IOException e) {
                return getView();
            }
        } else {
            LOGGER.info("Creating quest");
            req.setAttribute(EDIT, false);
            req.setAttribute(QUEST_JSON, JSON_SAMPLE);
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        LOGGER.info("Processing POST-request for saving quest. Параметры: {}", req.getParameterMap());
        String questJson = req.getParameter(QUEST_JSON);

        if (questJson == null || questJson.isEmpty()) {
            LOGGER.error(JSON_SAVE_ERROR + ". JSON parameter is missing or empty");
            req.getSession().setAttribute(ERROR, JSON_SAVE_ERROR);
            return getView();
        }

        try {
            Quest quest = questMapper.fromJsonString(questJson);
            LOGGER.debug("Quest is successfully received from JSON. Title: {}", quest.getTitle());
            if (quest.getId() != null) {
                LOGGER.info("Updating quest with ID: {}", quest.getId());
                questService.update(quest);
            } else {
                LOGGER.info("Creating new quest");
                questService.create(quest);
            }
            config.setQuestParameters(quest);
            LOGGER.info("Quest is successfully saved. ID: {}", quest.getId());
            return HOME;
        } catch (IOException e) {
            LOGGER.error(JSON_SAVE_ERROR + ". JSON: {}. Cause: {}",
                    questJson, e.getMessage(), e);
            req.getSession().setAttribute(QUEST_JSON, questJson);
            req.getSession().setAttribute(ERROR, JSON_SAVE_ERROR);
            return getView();
        }
    }
}
