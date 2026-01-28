package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.Topic;

public class TopicStats {

    private final String topicCode;
    private int total;
    private int passed;
    private String topicDisplayName;


    public TopicStats(String topicCode) {
        this.topicCode = topicCode;
        Topic topic = Topic.fromCode(topicCode);
        this.topicDisplayName = topic.getDisplayName();
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
        return (passed * 100) / total;
    }


}
