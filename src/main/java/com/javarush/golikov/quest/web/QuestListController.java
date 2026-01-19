package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.service.QuestService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/quests")
public class QuestListController extends HttpServlet {
    private QuestService questService;

    @Override
    public void init() {
        questService = (QuestService) getServletContext().getAttribute("questService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        req.setAttribute("quests", questService.getAllQuests());

        req.setAttribute("view", "/WEB-INF/jsp/quests.jsp");

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
