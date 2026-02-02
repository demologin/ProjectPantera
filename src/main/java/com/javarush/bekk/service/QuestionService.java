package com.javarush.bekk.service;

import com.javarush.bekk.entity.Question;
import com.javarush.bekk.repository.QuestionRepository;

public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question) {
        questionRepository.create(question);
    }

}
