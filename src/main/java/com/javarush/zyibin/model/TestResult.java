package com.javarush.zyibin.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private long id;
    private long userId;
    private String topicCode;
    private int totalQuestions;
    private int correctAnswers;
    private boolean passed;
    private LocalDateTime finishedAt;

    public TestResult(long userId,
                      String topicCode,
                      int totalQuestions,
                      int correctAnswers,
                      boolean passed,
                      LocalDateTime finishedAt) {
        this.userId = userId;
        this.topicCode = topicCode;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.passed = passed;
        this.finishedAt = finishedAt;
    }

    public void setId(long id) {
        if (this.id != 0) {
            throw new IllegalStateException("id is already set");
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public boolean isPassed() {
        return passed;
    }
    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public String getTopicDisplayName() {
        String[] codes = topicCode.split(",");
        List<String> names = new ArrayList<>();

        for (String code : codes) {
            Topic topic = Topic.fromCode(code.trim());
            names.add(topic.getDisplayName());
        }

        return String.join(", ", names);
    }
}
