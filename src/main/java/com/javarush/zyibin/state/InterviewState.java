package com.javarush.zyibin.state;

import com.javarush.zyibin.model.Question;

import java.util.List;

public class InterviewState {
    private final List<Question> questions;
    private int currentIndex;
    private int score;

    public InterviewState(List<Question> questions) {
        this.questions = questions;
        this.currentIndex = 0;
        this.score = 0;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public boolean isFinished() {
        return currentIndex >= questions.size();
    }

    public void moveToNextQuestion() {
        currentIndex++;
    }

    public void incrementScore() {
        score++;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getScore() {
        return score;
    }
}
