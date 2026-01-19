package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.service.StatisticsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/statistics")
public class StatisticsController extends HttpServlet {

    private StatisticsService statisticsService;

    @Override
    public void init() {
        statisticsService =
                (StatisticsService) getServletContext().getAttribute("statisticsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws jakarta.servlet.ServletException, java.io.IOException {

        req.setAttribute("stats", statisticsService.getStats());
        req.setAttribute("view", "/WEB-INF/jsp/statistics.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}