package com.javarush.zyibin.controllers.admin;

public class TopicStats {

    private final String topicCode;
    private int total;
    private int passed;

    public TopicStats(String topicCode) {
        this.topicCode = topicCode;
    }

    public void incrementTotal() {
        total++;
    }

    public void incrementPassed() {
        passed++;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public int getSuccessRate() {
        if (total == 0) {
            return 0;
        }
        return (passed * 100) / total;
    }
}
