package com.javarush.toporov.quest.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/restart")
public class RestartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("stepId");
        session.removeAttribute("result");

        Integer gamesCount = (Integer) session.getAttribute("gamesCount");
        session.setAttribute("gamesCount", (gamesCount == null ? 0 : gamesCount) + 1);
        response.sendRedirect("game");
    }
    }
