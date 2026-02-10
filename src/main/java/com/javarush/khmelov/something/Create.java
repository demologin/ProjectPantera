package com.javarush.khmelov.something;

import com.javarush.khmelov.cmd.Quest;
import com.javarush.khmelov.repository.QuestRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create")
public class Create extends HttpServlet {

    public static QuestRepository questRepository = new QuestRepository();
    Quest quest;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       quest = new Quest(NewQuest.name);

        quest.setWin(req.getParameter("e"));
        for (int i = 0; i < NewQuest.num; i++) {
            String first = req.getParameter("a" + i);
            String second = req.getParameter("b" + i);
            String third = req.getParameter("c" + i);
            String fourth = req.getParameter("d" + i);
            quest.crete(i, first, second, third,fourth);
        }
        questRepository.crate(quest);
        req.getRequestDispatcher("/WEB-INF/start-page.jsp").forward(req, resp);
    }

}
