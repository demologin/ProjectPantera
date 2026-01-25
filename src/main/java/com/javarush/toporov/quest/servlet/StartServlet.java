package com.javarush.toporov.quest.servlet;

import com.javarush.toporov.quest.model.Quest;
import com.javarush.toporov.quest.util.QuestData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/start")
public class StartServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String questName = request.getParameter("questName");


        Quest quest = QuestData.getQuest(questName);
        if (quest == null) {
            response.sendRedirect("index.jsp");
            return;
        }


        session.setAttribute("questName", questName);
        session.setAttribute("questPrologue", quest.getPrologue());
        session.setAttribute("stepId", 1);
        session.removeAttribute("result");


        response.sendRedirect("prologue.jsp");
    }
}