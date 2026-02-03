package com.javarush.chebotarev.quest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {

    private int id;
    private String text;
    private int nextNodeId;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getNextNodeId() {
        return nextNodeId;
    }
}
