package com.javarush.zyibin.service;

import com.javarush.zyibin.model.TestResult;
import com.javarush.zyibin.model.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTestStatisticsService {

    public List<UserTestStats> calculate(List<TestResult> results) {

        Map<String, UserTestStats> map = new HashMap<>();

        for (TestResult result : results) {

            // ключ = тест КАК ЕГО ЗАПУСТИЛ ПОЛЬЗОВАТЕЛЬ
            String testKey = buildTestName(result.getTopicCode());

            UserTestStats stats =
                    map.computeIfAbsent(testKey, UserTestStats::new);

            stats.incrementTotal();
            if (result.isPassed()) {
                stats.incrementPassed();
            }
        }

        return new ArrayList<>(map.values());
    }

    private String buildTestName(String topicCode) {
        String[] codes = topicCode.split(",");
        List<String> names = new ArrayList<>();

        for (String code : codes) {
            Topic topic = Topic.fromCode(code.trim());
            names.add(topic.getDisplayName());
        }

        return String.join(", ", names);
    }
}

