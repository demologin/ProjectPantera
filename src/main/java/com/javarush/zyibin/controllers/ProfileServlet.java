package com.javarush.zyibin.controllers;

import com.javarush.zyibin.controllers.profile.UserTopicStats;
import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.TestResultRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");

        TestResultRepository repository = (TestResultRepository) getServletContext().getAttribute("testResultRepository");
        List<TestResult> results = repository.findByUserId(user.getId());

        Map<String, UserTopicStats> topicStatsMap = new HashMap<>();
        for (TestResult result : results) {
            String[] topics = result.getTopicCode().split(",");
            for (String rawTopic : topics) {
                String topicCode = rawTopic.trim();
                Topic topic = Topic.fromCode(topicCode);
                String displayName = topic.getDisplayName();
                UserTopicStats stats = topicStatsMap.computeIfAbsent(
                        displayName,
                        UserTopicStats::new
                );
                stats.incrementTotal();
                if (result.isPassed()) {
                    stats.incrementPassed();
                }
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("topicStats", topicStatsMap.values());

        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }
}
