package com.javarush.buslovskii.command;

import com.javarush.buslovskii.model.GameState;
import com.javarush.buslovskii.model.Quest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RestartCommand implements Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");
        Quest currentQuest = (Quest) session.getAttribute("currentQuest");

        if (gameState != null && currentQuest != null) {
            gameState.setCurrentQuestionId(1);
            gameState.setGameOver(false);
            gameState.setVictory(false);
            session.removeAttribute("finalMessage");
        } else {
            return "redirect:/start";
        }

        return "redirect:/game";
    }
}