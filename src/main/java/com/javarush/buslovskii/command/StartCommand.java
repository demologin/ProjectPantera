package com.javarush.buslovskii.command;

import com.javarush.buslovskii.model.GameState;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class StartCommand implements Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        if ("POST".equalsIgnoreCase(method)) {
            String playerName = request.getParameter("playerName");

            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Игрок";
            }

            HttpSession session = request.getSession();

            GameState gameState = new GameState();
            gameState.setPlayerName(playerName.trim());

            session.setAttribute("gameState", gameState);

            return "redirect:/selectQuest";
        }

        return "/index.jsp";
    }
}