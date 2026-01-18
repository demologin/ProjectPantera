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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/start")
public class StartServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String[] topicParams = req.getParameterValues("topics");
        if (topicParams == null || topicParams.length == 0) {
            throw new IllegalArgumentException("No topics selected");
        }
        Set<Topic> selectedTopics = Arrays.stream(topicParams)
                .map(Topic::valueOf)
                .collect(Collectors.toSet());

        int questionCount = Integer.parseInt(req.getParameter("questionCount"));

        List<Question> allQuestions = new ArrayList<>();
        for (Topic topic : selectedTopics) {
            allQuestions.addAll(QuestionRepository.getQuestions(topic));
        }
        if (allQuestions.size() < questionCount) {
            throw new IllegalStateException("Not enough questions for the topic ");
        }

        Collections.shuffle(allQuestions);
        List<Question> selectedQuestions = allQuestions.subList(0, questionCount);

        InterviewState interviewState = new InterviewState(selectedTopics, selectedQuestions);
        SessionUtils.setInterviewState(session, interviewState);

        resp.sendRedirect(req.getContextPath() + "/question");
    }

}