package com.javarush.zyibin.controllers.profile;

public class UserTopicStats {

    private final String topicDisplayName;
    private int total;
    private int passed;

    public UserTopicStats(String topicDisplayName) {
        this.topicDisplayName = topicDisplayName;
    }

    public void incrementTotal() {
        total++;
    }

    public void incrementPassed() {
        passed++;
    }

    public String getTopicDisplayName() {
        return topicDisplayName;
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
        return  passed * 100 / total;
    }
}
