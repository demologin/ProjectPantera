package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.service.QuestService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import com.javarush.golikov.quest.model.*;

@WebServlet("/start")
public class StartQuestController extends HttpServlet {
    private QuestService questService;

    @Override
    public void init() {
        questService = (QuestService) getServletContext().getAttribute("questService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String id = req.getParameter("id");

        QuestSession qs = questService.startQuest(id);

        req.getSession().setAttribute("quest", qs);
        resp.sendRedirect(req.getContextPath() + "/play");
    }
}