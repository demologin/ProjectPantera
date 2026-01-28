package com.javarush.zyibin.service;

public class UserTopicStats extends BaseStats {

    private final String topicDisplayName;

    public UserTopicStats(String topicDisplayName) {
        this.topicDisplayName = topicDisplayName;
    }

    public String getTopicDisplayName() {
        return topicDisplayName;
    }

}
