package com.javarush.chebotarev.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.Path;
import com.javarush.chebotarev.component.QuestService;
import com.javarush.chebotarev.quest.Quest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(Go.EDITOR)
public class EditorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.EDITOR)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = ObjectRepository.find(ObjectMapper.class);
        Quest quest = mapper.readValue(req.getReader(), Quest.class);
        QuestService questService = ObjectRepository.find(QuestService.class);
        questService.saveQuest(quest, mapper);
    }
}
