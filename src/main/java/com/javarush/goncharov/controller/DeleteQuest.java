package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Quest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/delete-quest")
public class DeleteQuest extends DefaultServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idQuest = Long.parseLong(req.getParameter("id"));
        Optional<Quest> userFind = questService.get(idQuest);
        if (req.getParameter("action").equals("delete")) {
            userFind.ifPresent(questService::delete);
        }
        resp.sendRedirect("/list-quests");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idQuest = Long.parseLong(req.getParameter("id"));
        questService.get(idQuest).ifPresent(quest -> req.setAttribute("quest", quest));
        req.getRequestDispatcher("/WEB-INF/delete-quest.jsp").forward(req, resp);
    }
}
