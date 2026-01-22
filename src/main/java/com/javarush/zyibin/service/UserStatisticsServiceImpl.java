package com.javarush.zyibin.service;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStatisticsServiceImpl implements UserStatisticsService{

    @Override
    public List<UserTopicStats> calculateUserTopicStats(List<TestResult> results) {
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
        return new ArrayList<>(statsByTopic.values());
    }
}
