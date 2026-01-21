package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.TestResultRepository;
import com.javarush.zyibin.session.SessionUtils;
import com.javarush.zyibin.state.InterviewState;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        InterviewState interviewState = (InterviewState) session.getAttribute("interviewState");
        if (interviewState == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        int totalQuestions = interviewState.getTotalQuestions();
        int correctAnswers = interviewState.getScore();
        boolean passed = correctAnswers * 2 >= totalQuestions;

        String topicCodes = interviewState.getTopics()
                .stream()
                .map(Topic::getCode)
                .collect(Collectors.joining(", "));

        TestResult result = new TestResult(
                user.getId(),
                topicCodes,
                totalQuestions,
                correctAnswers,
                passed,
                LocalDateTime.now()
        );

        TestResultRepository repository = (TestResultRepository) getServletContext().getAttribute("testResultRepository");
        repository.save(result);

        req.setAttribute("topics", topicCodes);
        req.setAttribute("total", totalQuestions);
        req.setAttribute("correct", correctAnswers);
        req.setAttribute("passed", passed);

        session.removeAttribute("interviewState");

        req.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(req, resp);
    }
//
}
