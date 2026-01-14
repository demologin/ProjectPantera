package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");

        if (questions == null || currentIndex == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        if (currentIndex >= questions.size()) {
            resp.sendRedirect(req.getContextPath() + "/result");
            return;
        }
        Question currentQuestion = questions.get(currentIndex);
        req.setAttribute("question", currentQuestion);
        req.setAttribute("questionNumber", currentIndex + 1);
        req.setAttribute("totalQuestions", questions.size());
        req.getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        Integer score = (Integer) session.getAttribute("score");
        if (questions == null || currentIndex == null || score == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        String answerIndexParam = req.getParameter("answerIndex");
        if (answerIndexParam == null) {
            resp.sendRedirect(req.getContextPath() + "/question");
            return;
        }
        int selectedAnswerIndex = Integer.parseInt(answerIndexParam);
        Question currentQuestion = questions.get(currentIndex);

        if (selectedAnswerIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
        }
        currentIndex++;

        session.setAttribute("currentIndex", currentIndex);
        session.setAttribute("score", score);

        resp.sendRedirect(req.getContextPath() + "/question");


    }
}
