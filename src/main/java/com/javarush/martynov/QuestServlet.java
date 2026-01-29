package com.javarush.martynov;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "QuestServlet", value = "/quest")
public class QuestServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(QuestServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        GameState state = (GameState) session.getAttribute("state");
        String sessionId = session.getId();

        if (state == null) {
            state = new GameState();
            session.setAttribute("state", state);
            logger.info("New game state created for session: {}", sessionId);
        }

        String action = req.getParameter("action");
        logger.debug("Received action: {} for session: {}", action, sessionId);

        if ("start".equals(action)) {
            String name = req.getParameter("name");
            state.setPlayerName(name);
            state.setStep(1);
            logger.info("Player '{}' has started the journey (Session: {})", name, sessionId);

        } else if ("answer".equals(action)) {
            String choice = req.getParameter("choice");
            logger.debug("Player '{}' made a choice: {} on step {}", state.getPlayerName(), choice, state.getStep());
            processGameStep(state, choice);

        } else if ("restart".equals(action)) {
            logger.info("Player '{}' requested a restart. Games played before restart: {}", state.getPlayerName(), state.getGamesPlayed());
            state.resetGame();
            state.incrementGames();
        }

        req.getRequestDispatcher("/quest.jsp").forward(req, resp);
    }

    private void updateGlobalStatistics(String name, boolean isWin) {
        if (name == null || name.isEmpty()) {
            logger.warn("Attempted to update statistics for a null or empty player name.");
            return;
        }

        ServletContext context = getServletContext();
        Map<String, UserStats> statsMap = (Map<String, UserStats>) context.getAttribute("globalStats");

        if (statsMap == null) {
            statsMap = new ConcurrentHashMap<>();
            context.setAttribute("globalStats", statsMap);
            logger.debug("Global statistics map initialized in ServletContext.");
        }

        UserStats userStats = statsMap.computeIfAbsent(name, k -> {
            logger.debug("Creating new UserStats entry for player: {}", name);
            return new UserStats();
        });

        if (isWin) {
            userStats.addWin();
            logger.info("Global stats updated: Player '{}' WON.", name);
        } else {
            userStats.addLoss();
            logger.info("Global stats updated: Player '{}' LOST.", name);
        }
    }

    private void processGameStep(GameState state, String choice) {
        int currentStep = state.getStep();
        String name = state.getPlayerName();

        switch (currentStep) {
            case 1:
                if ("trust".equals(choice)) {
                    state.setStep(2);
                } else {
                    state.setDeathReason("Вы вышли на открытое пространство и стали легкой добычей для стаи зомби.");
                    state.setStep(-1);
                    logger.warn("Player '{}' died at Step 1. Choice: {}", name, choice);
                    updateGlobalStatistics(name, false);
                }
                break;

            case 2:
                if ("truth".equals(choice)) {
                    state.setStep(3);
                } else {
                    state.setDeathReason("Вступать в рукопашную с зомби было плохой идеей. Вас повалили числом.");
                    state.setStep(-1);
                    logger.warn("Player '{}' died at Step 2. Choice: {}", name, choice);
                    updateGlobalStatistics(name, false);
                }
                break;

            case 3:
                if ("truth".equals(choice)) {
                    state.setStep(4);
                } else {
                    state.setDeathReason("В сумерках вы не заметили ловушку в заброшенном здании и погибли.");
                    state.setStep(-1);
                    logger.warn("Player '{}' died at Step 3. Choice: {}", name, choice);
                    updateGlobalStatistics(name, false);
                }
                break;

            case 4:
                if ("truth".equals(choice)) {
                    state.setStep(5);
                } else {
                    state.setDeathReason("Охранник почувствовал ложь в вашем голосе. Он решил не рисковать и выстрелил.");
                    state.setStep(-1);
                    logger.warn("Player '{}' died at Step 4. Choice: {}", name, choice);
                    updateGlobalStatistics(name, false);
                }
                break;

            case 5:
                if ("scan".equals(choice)) {
                    state.setStep(6);
                    logger.info("SUCCESS! Player '{}' reached the bunker.", name);
                    updateGlobalStatistics(name, true);
                } else {
                    state.setDeathReason("Ваша попытка прорваться силой закончилась быстро. Охранники на вышках не промахиваются.");
                    state.setStep(-1);
                    logger.warn("Player '{}' died at Step 5 (The Scanner). Choice: {}", name, choice);
                    updateGlobalStatistics(name, false);
                }
                break;

            default:
                logger.error("Unexpected game step {} encountered for player '{}'", currentStep, name);
                break;
        }
    }
}