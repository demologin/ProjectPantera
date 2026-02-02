package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class Home implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Home.class.getName());

    private final QuestService questService;

    public Home(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        LOGGER.info("Received GET request to display all quests");
        List<Quest> quests = questService.getAll();
        req.setAttribute(QUESTS, quests);
        LOGGER.debug("Retrieved {} quests from service", quests.size());
        return getView();
    }

    @Override
    public String doDelete(HttpServletRequest req) {
        LOGGER.info("Received DELETE request for quest");

        Helpers.checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);
        LOGGER.debug("Admin authorization successful");

        String questIdStr = req.getParameter(QUEST_ID);
        LOGGER.debug("Attempting to delete quest with ID: {}", questIdStr);

        Quest quest = questService.getValidatedQuest(questIdStr)
                .orElseThrow(() -> new IllegalArgumentException(QUEST_NOT_FOUND + questIdStr));
        req.setAttribute(QUEST, quest);
        questService.delete(quest);
        LOGGER.info("Quest with ID {} successfully deleted", questIdStr);

        return getView();
    }
}
