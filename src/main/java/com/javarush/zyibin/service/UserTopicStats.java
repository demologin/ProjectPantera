package com.javarush.zyibin.service;

public class UserTopicStats {

    private final String topicDisplayName;
    private int totalAttempts;
    private int successfulAttempts;

    public UserTopicStats(String topicDisplayName) {
        this.topicDisplayName = topicDisplayName;
    }

    public void incrementTotal() {
        totalAttempts++;
    }

    public void incrementSuccessful() {
        successfulAttempts++;
    }

    public String getTopicDisplayName() {
        return topicDisplayName;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getSuccessfulAttempts() {
        return successfulAttempts;
    }

    public int getSuccessRate() {
        if (totalAttempts == 0) {
            return 0;
        }
        return (successfulAttempts * 100) / totalAttempts;
    }
}
