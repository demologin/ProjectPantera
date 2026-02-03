package com.javarush.chebotarev.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.QuestService;
import com.javarush.chebotarev.quest.Quest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@SuppressWarnings("unused")
public class Editor extends Command {

    @Override
    public String doGet(HttpServletRequest req, HttpServlet servlet) {
        return Go.EDITOR;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = ObjectRepository.find(ObjectMapper.class);
            Quest quest = mapper.readValue(req.getReader(), Quest.class);
            QuestService questService = ObjectRepository.find(QuestService.class);
            questService.saveQuest(quest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Go.ROOT;
    }
}
