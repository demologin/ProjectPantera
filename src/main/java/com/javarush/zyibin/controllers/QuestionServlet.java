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

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (!SessionUtils.hasInterview(session)) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        InterviewState state = SessionUtils.getInterviewState(session);

        if (state.isFinished()) {
            resp.sendRedirect(req.getContextPath() + "/result");
            return;
        }

        req.setAttribute("question", state.getCurrentQuestion());
        req.setAttribute("questionNumber", state.getCurrentIndex() + 1);
        req.setAttribute("totalQuestions", state.getTotalQuestions());

        req.getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (!SessionUtils.hasInterview(session)) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        String answerIndexParam = req.getParameter("answerIndex");

        if (answerIndexParam == null) {
            resp.sendRedirect(req.getContextPath() + "/question");
            return;
        }
        int selectedIndex = Integer.parseInt(answerIndexParam);
        InterviewState state = SessionUtils.getInterviewState(session);

        if (selectedIndex == state.getCurrentQuestion().getCorrectAnswerIndex()) {
            state.incrementScore();
        }

        state.moveToNextQuestion();

        resp.sendRedirect(req.getContextPath() + "/question");


    }
}
