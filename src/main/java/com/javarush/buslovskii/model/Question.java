package com.javarush.buslovskii.model;

import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String text;
    private String option1;
    private String option2;
    private int nextIdOption1;
    private int nextIdOption2;
    private boolean isFinal;
    private String victoryMessage;
    private String defeatMessage;
    
    public Question(int id, String text, String option1, String option2, 
                    int nextIdOption1, int nextIdOption2) {
        this(id, text, option1, option2, nextIdOption1, nextIdOption2, false, null, null);
    }
    
    public Question(int id, String text, String option1, String option2, 
                    int nextIdOption1, int nextIdOption2, boolean isFinal,
                    String victoryMessage, String defeatMessage) {
        this.id = id;
        this.text = text;
        this.option1 = option1;
        this.option2 = option2;
        this.nextIdOption1 = nextIdOption1;
        this.nextIdOption2 = nextIdOption2;
        this.isFinal = isFinal;
        this.victoryMessage = victoryMessage;
        this.defeatMessage = defeatMessage;
    }
    
    public int getId() { return id; }
    public String getText() { return text; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public int getNextIdOption1() { return nextIdOption1; }
    public int getNextIdOption2() { return nextIdOption2; }
    public boolean isFinal() { return isFinal; }
    public String getVictoryMessage() { return victoryMessage; }
    public String getDefeatMessage() { return defeatMessage; }
}