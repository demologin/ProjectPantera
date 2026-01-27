package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.state.InterviewState;
import com.javarush.zyibin.validation.QuestionValidator;
import com.javarush.zyibin.factory.ValidationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис для обработки вопросов и ответов
 */
public class QuestionService {
    
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    private final QuestionValidator questionValidator;
    
    public QuestionService() {
        this.questionValidator = ValidationFactory.createQuestionValidator();
    }
    
    /**
     * Обрабатывает ответ пользователя на вопрос
     * @param state состояние интервью
     * @param answerIndexStr ответ пользователя в виде строки
     * @throws ValidationException если ответ невалидный
     */
    public void processAnswer(InterviewState state, String answerIndexStr) {
        // Валидация ответа
        questionValidator.validateAnswer(answerIndexStr, state.getCurrentQuestion().getAnswers().size() - 1);
        
        int selectedIndex = Integer.parseInt(answerIndexStr);
        
        if (selectedIndex == state.getCurrentQuestion().getCorrectAnswerIndex()) {
            log.debug("Correct answer selected");
            state.incrementScore();
        } else {
            log.debug("Incorrect answer selected");
        }
        
        state.moveToNextQuestion();
        log.debug("Moving to next question, current index is now {}", state.getCurrentIndex());
    }
}
