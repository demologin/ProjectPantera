package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.repository.QuestionRepository;
import com.javarush.vasileva.repository.Repository;

import java.util.List;
import java.util.Optional;

public class QuestionService implements BaseService<Question> {

    private final Repository<Question> questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getAll() {
        return questionRepository.getAll();
    }

    @Override
    public Optional<Question> get(Long id) {
        return questionRepository.get(id);
    }

    @Override
    public void create(Question question) {
        questionRepository.create(question);
    }
}
