package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.service.UserStatisticsService;
import com.javarush.zyibin.service.UserStatisticsServiceImpl;
import com.javarush.zyibin.service.UserTestStatisticsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {

    private UserStatisticsService topicStatisticsService;
    private UserTestStatisticsService testStatisticsService;

    @Override
    protected void initializeSpecificServices() {
        this.topicStatisticsService = new UserStatisticsServiceImpl();
        this.testStatisticsService = new UserTestStatisticsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("GET /profile");

        User user = getCurrentUser(req);
        log.debug("Loading test results for user {}", user.getUsername());

        List<TestResult> results = testResultRepository.findByUserId(user.getId());
        req.setAttribute("results", results);

        req.setAttribute("testStats", testStatisticsService.calculate(results));
        req.setAttribute("topicStats", topicStatisticsService.calculateUserTopicStats(results));

        log.info("Profile page prepared for user {}", user.getUsername());
        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }
}
