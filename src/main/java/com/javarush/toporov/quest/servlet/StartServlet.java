package com.javarush.toporov.quest.servlet;

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

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String playerName = (String) session.getAttribute("user");
        String questName = request.getParameter("questName");

        if (playerName == null || playerName.isEmpty() || questName == null || questName.isEmpty()) {

            request.setAttribute("error", "Пожалуйста, введите имя и выберите квест.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        session.setAttribute("playerName", playerName);
        session.setAttribute("questName", questName);
        session.setAttribute("currentId", 1); // стартовый ID вопроса


        response.sendRedirect(request.getContextPath() + "/game");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }
}