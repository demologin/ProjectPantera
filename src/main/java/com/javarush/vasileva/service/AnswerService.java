package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Answer;
import com.javarush.vasileva.repository.AnswerRepository;
import com.javarush.vasileva.repository.Repository;

import java.util.List;
import java.util.Optional;

public class AnswerService {
    private final Repository<Answer> answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> getAll() {
        return answerRepository.getAll();
    }

    public Optional<Answer> get(Long id) {
        return answerRepository.get(id);
    }

    public void create(Answer answer) {
        answerRepository.create(answer);
    }

    public Long parseAnswerIdStrToLong(String answerIdStr) {
        try {
            return Long.parseLong(answerIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid answer id");
        }
    }
}
