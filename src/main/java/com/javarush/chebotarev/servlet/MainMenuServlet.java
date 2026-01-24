package com.javarush.chebotarev.servlet;

import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.Path;
import com.javarush.chebotarev.component.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(Go.MAIN_MENU)
public class MainMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        QuestService questService = ObjectRepository.find(QuestService.class);
        List<String> availableQuests = questService.obtainAvailableQuests(getServletContext());
        currentSession.setAttribute("availableQuests", availableQuests);
        req.getRequestDispatcher(Path.MAIN_MENU)
                .forward(req, resp);
    }
}
