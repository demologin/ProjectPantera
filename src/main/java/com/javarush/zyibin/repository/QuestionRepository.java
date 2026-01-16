package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.source.FileQuestionSource;
import com.javarush.zyibin.source.QuestionSource;

import java.util.List;

public class QuestionRepository {
    private static final QuestionSource source = new FileQuestionSource();

    public static List<Question> getQuestions(Topic topic) {
        return source.loadQuestions(topic);
    }
}
