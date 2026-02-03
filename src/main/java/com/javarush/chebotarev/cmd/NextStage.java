package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.component.*;
import com.javarush.chebotarev.quest.CurrentQuest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class NextStage extends Command {

    @Override
    public String doGet(HttpServletRequest req, HttpServlet servlet) {
        HttpSession currentSession = req.getSession();
        CurrentQuest currentQuest = Utils.extractAttribute(
                currentSession,
                Attribute.CURRENT_QUEST,
                CurrentQuest.class
        );
        int nextNodeId = getSelectedNextNodeId(req);
        currentQuest.nextStage(nextNodeId);
        String view;
        if (currentQuest.isDone()) {
            view = Go.RESULT;
            Statistics statistics = Utils.extractAttribute(
                    currentSession,
                    Attribute.STATISTICS,
                    Statistics.class
            );
            if (currentQuest.isVictory()) {
                statistics.incVictories();
            } else {
                statistics.incDefeats();
            }
        } else {
            view = Go.QUEST;
        }
        return view;
    }

    private int getSelectedNextNodeId(HttpServletRequest req) {
        String nextNodeIdString = req.getParameter(Parameter.NEXT_NODE_ID);
        return Integer.parseInt(nextNodeIdString);
    }
}
