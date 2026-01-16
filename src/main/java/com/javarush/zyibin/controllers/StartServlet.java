package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.repository.QuestionRepository;
import com.javarush.zyibin.session.SessionUtils;
import com.javarush.zyibin.state.InterviewState;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/start")
public class StartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("topics", Topic.values());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/start.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String topicParam = req.getParameter("topic");
        Topic topic = Topic.valueOf(topicParam);

        List<Question> allQuestions = QuestionRepository.getQuestions(topic);
        if (allQuestions.size() < 20) {
            throw new IllegalStateException("Not enough questions for the topic " + topic);
        }

        List<Question> shuffled = new ArrayList<>(allQuestions);
        Collections.shuffle(shuffled);
        List<Question> selectedQuestions = shuffled.subList(0, 20);

        InterviewState interviewState = new InterviewState(topic, selectedQuestions);
        SessionUtils.setInterviewState(session, interviewState);

        resp.sendRedirect(req.getContextPath() + "/question");
    }

}