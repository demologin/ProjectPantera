package com.javarush.khmelov.quest;


public class Answer {
    String text;
    int id;
    public Answer(String text,int id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}

