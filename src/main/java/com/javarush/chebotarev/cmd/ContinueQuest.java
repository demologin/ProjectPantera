package com.javarush.chebotarev.cmd;

import com.javarush.chebotarev.component.Attribute;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.Utils;
import com.javarush.chebotarev.quest.CurrentQuest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class ContinueQuest extends Command {

    @Override
    public String doGet(HttpServletRequest req, HttpServlet servlet) {
        HttpSession currentSession = req.getSession();
        CurrentQuest currentQuest = Utils.extractAttribute(
                currentSession,
                Attribute.CURRENT_QUEST,
                CurrentQuest.class
        );
        String view;
        if (!currentQuest.isStarted()) {
            view = Go.NEW_QUEST;
        } else if (!currentQuest.isDone()) {
            view = Go.QUEST;
        } else {
            view = Go.RESULT;
        }
        return view;
    }
}
