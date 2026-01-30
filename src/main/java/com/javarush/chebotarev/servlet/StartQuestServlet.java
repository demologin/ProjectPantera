package com.javarush.chebotarev.servlet;

import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.Path;
import com.javarush.chebotarev.component.Utils;
import com.javarush.chebotarev.quest.CurrentQuest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(Go.START_QUEST)
public class StartQuestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        CurrentQuest currentQuest = Utils.extractAttribute(
                currentSession,
                "currentQuest",
                CurrentQuest.class
        );
        currentQuest.start();
        if (currentQuest.isDone()) {
            throw new RuntimeException("Bad quest: first stage is a finish stage");
        }
        req.getRequestDispatcher(Path.QUEST)
                .forward(req, resp);
    }
}
