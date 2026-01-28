package com.javarush.zyibin.dto;

import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.service.BaseStats;

public class TopicStats extends BaseStats {

    private final String topicCode;
    private String topicDisplayName;


    public TopicStats(String topicCode) {
        this.topicCode = topicCode;
        Topic topic = Topic.fromCode(topicCode);
        this.topicDisplayName = topic.getDisplayName();
    }

    public String getTopicCode() {
        return topicCode;
    }

    public String getTopicDisplayName() {
        return topicDisplayName;
    }

}
