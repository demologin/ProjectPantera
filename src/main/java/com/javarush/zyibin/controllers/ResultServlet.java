package com.javarush.zyibin.controllers;

import com.javarush.zyibin.session.SessionUtils;
import com.javarush.zyibin.state.InterviewState;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (!SessionUtils.hasInterview(session)) {
            resp.sendRedirect(req.getContextPath() + "/start");
        }
        InterviewState state = SessionUtils.getInterviewState(session);

        if (!state.isFinished()) {
            resp.sendRedirect(req.getContextPath() + "/question");
            return;
        }
        int score = state.getScore();
        int totalQuestions = state.getTotalQuestions();
        boolean passed = score >= totalQuestions / 2.0;

        req.setAttribute("topics", state.getTopics());
        req.setAttribute("score", score);
        req.setAttribute("totalQuestions", totalQuestions);
        req.setAttribute("passed", passed);

        SessionUtils.clearInterview(session);
        req.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(req, resp);
    }
}
