package com.javarush.ushanov.service;

import com.javarush.ushanov.entity.GameSession;
import com.javarush.ushanov.entity.StepStatus;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionService.class);

    public static final String SESSION_KEY = "gameSession";

    private final QuestService questService;

    public SessionService(QuestService questService) {
        this.questService = questService;
    }

    public GameSession createSession(HttpSession httpSession, String playerName) {
        GameSession gameSession = new GameSession(playerName, questService.getStartStepId());
        gameSession.startNewGame(questService.getStartStepId());
        httpSession.setAttribute(SESSION_KEY, gameSession);
        log.info("New session created for player '{}'", playerName);
        return gameSession;
    }

    public GameSession getSession(HttpSession httpSession) {
        return (GameSession) httpSession.getAttribute(SESSION_KEY);
    }

    public void processChoice(HttpSession httpSession, String chosenOption) {
        GameSession gameSession = getSession(httpSession);
        if (gameSession == null) {
            log.warn("processChoice called without active session");
            return;
        }

        int nextStepId = questService.getNextStepId(gameSession.getCurrentStepId(), chosenOption);
        gameSession.setCurrentStepId(nextStepId);

        var nextStep = questService.getStep(nextStepId);
        if (nextStep.getStatus() == StepStatus.WIN) {
            gameSession.registerWin();
            log.info("Player '{}' won! Total wins: {}",
                    gameSession.getPlayerName(), gameSession.getGamesWon());
        } else if (nextStep.getStatus() == StepStatus.LOSE) {
            log.info("Player '{}' lost at step {}", gameSession.getPlayerName(), nextStepId);
        }
    }

    public void restartGame(HttpSession httpSession) {
        GameSession gameSession = getSession(httpSession);
        if (gameSession != null) {
            gameSession.startNewGame(questService.getStartStepId());
            log.info("Player '{}' restarted the game. Games played: {}",
                    gameSession.getPlayerName(), gameSession.getGamesPlayed());
        }
    }
}
