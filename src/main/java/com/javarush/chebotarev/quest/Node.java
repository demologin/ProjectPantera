package com.javarush.chebotarev.quest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    private int id;
    private String text;
    private String type;
    private List<Option> options;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public List<Option> getOptions() {
        return options;
    }

    public interface Type {
        String COMMON = "common";
        String VICTORY = "victory";
        String DEFEAT = "defeat";
    }
}
