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

@WebServlet(Go.NEXT_STAGE)
public class NextStageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        CurrentQuest currentQuest = Utils.extractAttribute(
                currentSession,
                "currentQuest",
                CurrentQuest.class
        );
        int nextNodeId = getSelectedNextNodeId(req);
        currentQuest.nextStage(nextNodeId);
        String path = currentQuest.isDone() ? Path.RESULT : Path.QUEST;
        req.getRequestDispatcher(path)
                .forward(req, resp);
    }

    private int getSelectedNextNodeId(HttpServletRequest req) {
        String nextNodeIdString = req.getParameter("nextNodeId");
        return Integer.parseInt(nextNodeIdString);
    }
}
