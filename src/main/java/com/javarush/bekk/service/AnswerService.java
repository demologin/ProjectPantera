package com.javarush.bekk.service;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.repository.QuestionRepository;

public class AnswerService {
    private final QuestionRepository questionRepository;

    public AnswerService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    }
