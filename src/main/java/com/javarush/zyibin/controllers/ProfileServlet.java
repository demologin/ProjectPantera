package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.TestResultRepository;
import com.javarush.zyibin.service.UserStatisticsService;
import com.javarush.zyibin.service.UserStatisticsServiceImpl;
import com.javarush.zyibin.service.UserTestStatisticsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserStatisticsService topicStatisticsService;
    private UserTestStatisticsService testStatisticsService;

    @Override
    public void init() throws ServletException {
        topicStatisticsService = new UserStatisticsServiceImpl();
        topicStatisticsService = new UserStatisticsServiceImpl();
        testStatisticsService = new UserTestStatisticsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");

        TestResultRepository repository = (TestResultRepository) getServletContext().getAttribute("testResultRepository");
        List<TestResult> results = repository.findByUserId(user.getId());
        req.setAttribute("results", results);

        req.setAttribute(
                "testStats",
                testStatisticsService.calculate(results)
        );

        req.setAttribute(
                "topicStats",
                topicStatisticsService.calculateUserTopicStats(results)
        );

        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }
}
