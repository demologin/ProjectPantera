package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Answer;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.repository.QuestionRepository;
import com.javarush.vasileva.util.Helpers;
import com.javarush.vasileva.util.Value;

import java.util.List;
import java.util.Optional;

public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(Question question) {
        questionRepository.create(question);
    }

    public List<Question> getAll() {
        return questionRepository.getAll();
    }

    public Optional<Question> get(Long id) {
        return questionRepository.findById(id);
    }

    public Optional<Question> getByQuestionLabelAndQuestId(String questionLabel, long questId) {
        return questionRepository.getByQuestionLabelAndQuestId(questionLabel, questId);
    }

    public Optional<Question> findCurrentQuestion(String questionIdStr, Quest quest) {
        Long currentQuestionId;
        if (questionIdStr != null && !questionIdStr.isEmpty()) {
            currentQuestionId = Helpers.parseStringToLong(questionIdStr);
        } else {
            currentQuestionId = quest.getStartQuestionId();
        }
        return get(currentQuestionId);
    }

    public long findNextQuestionId(long questId, Answer answer) {
        String nextQuestionLabelStr = answer.getNextQuestionLabel();
        Question nextQuestion = getByQuestionLabelAndQuestId(nextQuestionLabelStr, questId)
                .orElseThrow(() -> new AppException(Value.QUESTION_NOT_FOUND + nextQuestionLabelStr));
        return nextQuestion.getGeneratedId();
    }
}
