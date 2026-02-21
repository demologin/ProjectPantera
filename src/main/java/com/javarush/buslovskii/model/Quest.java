package com.javarush.buslovskii.model;

import java.io.Serializable;
import java.util.Map;

public interface Quest extends Serializable {
    String getId();
    String getTitle();
    String getDescription();
    String getGenre();
    int getDifficultyLevel(); // 1-5
    String getBackgroundImage();
    Map<Integer, Question> getQuestions();
    Question getStartQuestion();
    boolean isVictory(int questionId);
    String getVictoryMessage(int questionId);
    String getDefeatMessage(int questionId);
}