package com.javarush.buslovskii.command;

import com.javarush.buslovskii.model.GameState;
import com.javarush.buslovskii.model.Quest;
import com.javarush.buslovskii.model.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GameCommand implements Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");
        Quest currentQuest = (Quest) session.getAttribute("currentQuest");

        if (gameState == null) {
            return "redirect:/start";
        }

        if (currentQuest == null) {
            return "redirect:/selectQuest";
        }

        String method = request.getMethod();

        if ("POST".equalsIgnoreCase(method)) {
            String choiceParam = request.getParameter("choice");
            if (choiceParam != null) {
                try {
                    int choice = Integer.parseInt(choiceParam);
                    int currentQuestionId = gameState.getCurrentQuestionId();

                    Question currentQuestion = currentQuest.getQuestions().get(currentQuestionId);
                    if (currentQuestion == null) {
                        return "redirect:/selectQuest";
                    }

                    int nextQuestionId;
                    if (choice == 1) {
                        nextQuestionId = currentQuestion.getNextIdOption1();
                    } else {
                        nextQuestionId = currentQuestion.getNextIdOption2();
                    }

                    Question nextQuestion = currentQuest.getQuestions().get(nextQuestionId);
                    if (nextQuestion == null) {
                        return "redirect:/selectQuest";
                    }

                    gameState.setCurrentQuestionId(nextQuestionId);

                    if (nextQuestion.isFinal()) {
                        gameState.setGameOver(true);

                        boolean victory = currentQuest.isVictory(nextQuestionId);
                        gameState.setVictory(victory);

                        if (victory) {
                            gameState.addWin();
                            session.setAttribute("finalMessage", currentQuest.getVictoryMessage(nextQuestionId));
                        } else {
                            gameState.addLoss();
                            session.setAttribute("finalMessage", currentQuest.getDefeatMessage(nextQuestionId));
                        }

                        gameState.incrementGamesPlayed();
                        return "redirect:/game?result=final";
                    }
                } catch (NumberFormatException e) {
                    return "redirect:/game";
                }
            }
            return "redirect:/game";
        }

        if (gameState.isGameOver()) {
            Question currentQuestion = currentQuest.getQuestions().get(gameState.getCurrentQuestionId());
            if (currentQuestion == null) {
                return "redirect:/selectQuest";
            }
            request.setAttribute("question", currentQuestion);

            String finalMessage = (String) session.getAttribute("finalMessage");
            if (finalMessage != null) {
                request.setAttribute("finalMessage", finalMessage);
                session.removeAttribute("finalMessage");
            }

            return "/result.jsp";
        }

        Question currentQuestion = currentQuest.getQuestions().get(gameState.getCurrentQuestionId());
        if (currentQuestion == null) {
            return "redirect:/selectQuest";
        }

        request.setAttribute("question", currentQuestion);
        request.setAttribute("quest", currentQuest);
        return "/game.jsp";
    }
}