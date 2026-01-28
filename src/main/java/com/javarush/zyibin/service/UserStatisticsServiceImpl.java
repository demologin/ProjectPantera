package com.javarush.zyibin.service;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStatisticsServiceImpl implements UserStatisticsService {
    private static final Logger log = LoggerFactory.getLogger(UserStatisticsServiceImpl.class);

    @Override
    public List<UserTopicStats> calculateUserTopicStats(List<TestResult> results) {
        log.debug("Calculating user topic statistics, results count={}", results.size());
        Map<String, com.javarush.zyibin.service.UserTopicStats> statsByTopic = new HashMap<>();
        for (TestResult result : results) {
            String[] topicCodes = result.getTopicCode().split(",");
            for (String topicCode : topicCodes) {
                String code = topicCode.trim();
                Topic topic = Topic.fromCode(code);
                String displayName = topic.getDisplayName();
                UserTopicStats stats = statsByTopic.computeIfAbsent(
                        displayName,
                        com.javarush.zyibin.service.UserTopicStats::new
                );
                stats.incrementTotal();
                if (result.isPassed()) {
                    stats.incrementPassed();
                }
            }
        }
        log.debug("User topic statistics calculated, topics count={}", statsByTopic.size());
        return new ArrayList<>(statsByTopic.values());
    }
}
