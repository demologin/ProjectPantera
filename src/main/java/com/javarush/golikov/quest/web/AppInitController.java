package com.javarush.golikov.quest.web;

import jakarta.servlet.annotation.*;

import com.javarush.golikov.quest.repository.QuestRepository;
import jakarta.servlet.http.HttpServlet;


@WebServlet(urlPatterns = "/init", loadOnStartup = 1)
public class AppInitController extends HttpServlet {

    public void init() {
        QuestRepository.loadAll(getServletContext());
    }
}



