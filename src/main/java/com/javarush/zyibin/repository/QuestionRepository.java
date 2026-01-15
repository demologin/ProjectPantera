package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.source.FileQuestionSource;
import com.javarush.zyibin.source.QuestionSource;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private static final QuestionSource source = new FileQuestionSource();

    public static List<Question> getQuestions() {
        return source.loadQuestions();
    }
}
