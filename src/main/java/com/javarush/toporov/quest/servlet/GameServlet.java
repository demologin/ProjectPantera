package com.javarush.toporov.quest.servlet;

import com.javarush.toporov.quest.model.Quest;
import com.javarush.toporov.quest.model.QuestStep;
import com.javarush.toporov.quest.util.QuestData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String questName = (String) session.getAttribute("questName");
        if (questName == null) {
            questName = "Черная Орхидея";
            session.setAttribute("questName", questName);
        }

        Quest quest = QuestData.getQuest(questName);

        Integer stepId = (Integer) session.getAttribute("stepId");
        if (stepId == null) {
            stepId = 1; // Если потеряли id, начинаем с начала
            session.setAttribute("stepId", stepId);
        }

        QuestStep step = quest.getStep(stepId);
        request.setAttribute("step", step);

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String choice = request.getParameter("choice");
        HttpSession session = request.getSession();
        String questName = (String) session.getAttribute("questName");
        Quest quest = QuestData.getQuest(questName);
        Integer stepId = (Integer) session.getAttribute("stepId");
        QuestStep step = quest.getStep(stepId);

        Integer nextStep = step.getOptions().get(choice);

        if (nextStep == null || nextStep == -1) {
            session.setAttribute("result", "LOSE");
            response.sendRedirect("result.jsp");
            return;
        }
        if (nextStep == -2) {
            session.setAttribute("result", "WIN");
            response.sendRedirect("result.jsp");
            return;
        }

        session.setAttribute("stepId", nextStep);
        response.sendRedirect("game");
    }
}
