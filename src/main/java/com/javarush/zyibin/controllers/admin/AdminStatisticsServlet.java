package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.TestResultRepository;
import com.javarush.zyibin.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/statistics")
public class AdminStatisticsServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AdminStatisticsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET /admin/statistics");

        TestResultRepository resultRepository = (TestResultRepository) getServletContext().getAttribute("testResultRepository");

        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");

        List<TestResult> results = resultRepository.findAll();
        List<User> users = userRepository.findAll();

        int totalTests = results.size();
        long passedTests = results.stream()
                .filter(TestResult::isPassed)
                .count();

        Map<Long, UserStats> userStats = new HashMap<>();
        for (User user : users) {
            userStats.put(user.getId(), new UserStats(user));
        }

        for (TestResult r : results) {
            UserStats stats = userStats.get(r.getUserId());
            if (stats != null) {
                stats.incrementTotal();
                if (r.isPassed()) {
                    stats.incrementPassed();
                }
            }
        }

        Map<String, TopicStats> topicStats = new HashMap<>();

        for (TestResult r : results) {
            // поддержка смешанных тем
            String[] topics = r.getTopicCode().split(",");

            for (String rawTopic : topics) {
                String topic = rawTopic.trim();

                TopicStats stats =
                        topicStats.computeIfAbsent(topic, TopicStats::new);

                stats.incrementTotal();
                if (r.isPassed()) {
                    stats.incrementPassed();
                }
            }
        }

        log.info("Admin statistics prepared: totalTests={}, passedTests={}, users={}",
                totalTests,
                passedTests,
                users.size());

        req.setAttribute("totalTests", totalTests);
        req.setAttribute("passedTests", passedTests);
        req.setAttribute("userStats", userStats.values());
        req.setAttribute("topicStats", topicStats.values());

        req.getRequestDispatcher("/WEB-INF/jsp/admin/statistics.jsp")
                .forward(req, resp);
    }
}
