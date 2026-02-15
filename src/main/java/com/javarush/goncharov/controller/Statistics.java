package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/statistics")
public class Statistics extends DefaultServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userSession = (User) session.getAttribute("user");
        req.setAttribute("userStatistics", statisticService.getUserStat(userSession));
        req.setAttribute("usersStatistics", statisticService.getAllUserStat());
        req.getRequestDispatcher("/WEB-INF/statistics.jsp").forward(req, resp);
    }
}
