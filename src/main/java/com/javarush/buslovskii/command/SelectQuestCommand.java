package com.javarush.buslovskii.command;

import com.javarush.buslovskii.model.GameState;
import com.javarush.buslovskii.model.Quest;
import com.javarush.buslovskii.model.QuestManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SelectQuestCommand implements Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");
        QuestManager questManager = QuestManager.getInstance();

        if (gameState == null) {
            return "redirect:/start";
        }

        String method = request.getMethod();

        if ("POST".equalsIgnoreCase(method)) {
            String questId = request.getParameter("questId");
            if (questId == null || questId.trim().isEmpty()) {
                // Если ID не выбран, показываем список снова
                request.setAttribute("quests", questManager.getAllQuests());
                request.setAttribute("questStats", questManager.getAllQuestStats());
                return "/quest-selection.jsp";
            }

            Quest selectedQuest = questManager.getQuest(questId);

            if (selectedQuest != null) {
                session.setAttribute("currentQuest", selectedQuest);
                gameState.setCurrentQuestId(questId);
                gameState.setCurrentQuestionId(1);
                gameState.setGameOver(false);
                gameState.setVictory(false);

                questManager.incrementQuestPlays(questId);

                return "redirect:/game";
            }
        }

        request.setAttribute("quests", questManager.getAllQuests());
        request.setAttribute("questStats", questManager.getAllQuestStats());
        return "/quest-selection.jsp";
    }
}