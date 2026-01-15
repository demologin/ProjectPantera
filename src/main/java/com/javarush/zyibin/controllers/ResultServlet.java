package com.javarush.zyibin.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        Integer score = (Integer) session.getAttribute("score");
        Object questionsObj = session.getAttribute("questions");

        if (questionsObj == null || score == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        int totalQuestions = ((List<?>) questionsObj).size();
        boolean passed = score >= totalQuestions / 2;

        req.setAttribute("score", score);
        req.setAttribute("totalQuestions", totalQuestions);
        req.setAttribute("passed", passed);

        session.invalidate();
        req.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(req, resp);
    }
}
