package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.QuestRepository;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/list-quests")
public class ListQuests  extends HttpServlet {

    private final Storage storage = Storage.getInstance();
    private final QuestService questService = new QuestService(new QuestRepository(storage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Quest> quests = questService.getAll().values();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");
        req.setAttribute("quests", quests);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
