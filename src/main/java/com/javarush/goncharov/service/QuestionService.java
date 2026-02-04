package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Question;
import com.javarush.goncharov.repository.Repository;

import java.util.Map;
import java.util.Optional;

public class QuestionService {
    private final Repository<Question> questionRepository;

    public QuestionService(Repository<Question> questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> get(Long id){
        return questionRepository.get(id);
    }

    public Optional<Question> find(String nameQuest, String text){
        return questionRepository.findBy(nameQuest, text);
    }

    public void post(Question question){
        question.setId(0L);
        questionRepository.create(question);
    }

    public void delete(Question question){
        questionRepository.delete(question);
    }

    public void update(Question question){
        questionRepository.update(question);
    }

    public Map<Long, Question> getAll(){
        return questionRepository.getAll();
    }
}
