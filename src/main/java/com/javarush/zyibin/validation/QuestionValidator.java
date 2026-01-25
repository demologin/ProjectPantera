package com.javarush.zyibin.validation;

import com.javarush.zyibin.model.Question;

import java.util.List;

public class QuestionValidator {

    public static void validate(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalStateException("The list of questions is empty");
        }
        for (int i = 0; i < questions.size(); i++) {
            validateQuestion(questions.get(i),i);
        }
    }

    private static void validateQuestion(Question question, int index) {
        if (question == null) {
            throw new IllegalStateException("Index issue " + index + " equals null");
        }
        if (question.getQuestionText() == null ||
            question.getQuestionText().isBlank()) {
            throw new IllegalStateException("Blank question text (index=" + index + ")");
        }
        int correctIndex = question.getCorrectAnswerIndex();
        if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
            throw new IllegalStateException("Answers list is empty (index=" + index + ")");
        }
        if (correctIndex < 0 || correctIndex >= question.getAnswers().size()) {
            throw new IllegalStateException("Incorrect correctAnswerIndex (index=" + index + ")");
        }
    }
}
