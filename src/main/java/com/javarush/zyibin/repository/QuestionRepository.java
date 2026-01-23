package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.source.FileQuestionSource;
import com.javarush.zyibin.source.QuestionSource;

import java.util.List;

public class QuestionRepository {
    private  final QuestionSource source;

    public QuestionRepository(QuestionSource source) {
        this.source = source;
    }

    public static QuestionRepository defaultRepository() {
        return new QuestionRepository(new FileQuestionSource());
    }

    public List<Question> getQuestions(Topic topic) {
        return source.loadQuestions(topic);
    }
}
