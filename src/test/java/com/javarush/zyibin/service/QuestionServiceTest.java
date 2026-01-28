package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.model.InterviewState;
import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.validation.QuestionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    private QuestionService questionService;
    private InterviewState interviewState;
    private Question currentQuestion;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService();
        
        currentQuestion = new Question(
                "What is 2+2?",
                List.of("3", "4", "5", "6"),
                1
        );
        
        Question nextQuestion = new Question(
                "What is 3+3?",
                List.of("5", "6", "7", "8"),
                1
        );
        
        Topic topic = Topic.JAVA_CORE;
        
        interviewState = new InterviewState(
                Set.of(topic),
                List.of(currentQuestion, nextQuestion)
        );
    }

    @Test
    void shouldIncrementScore_whenCorrectAnswerIsSelected() {
        String correctAnswer = "1";
        
        questionService.processAnswer(interviewState, correctAnswer);
        
        assertEquals(1, interviewState.getScore());
        assertEquals(1, interviewState.getCurrentIndex());
        assertFalse(interviewState.isFinished());
    }

    @Test
    void shouldNotIncrementScore_whenIncorrectAnswerIsSelected() {
        String incorrectAnswer = "0";
        
        int initialScore = interviewState.getScore();
        
        questionService.processAnswer(interviewState, incorrectAnswer);
        
        assertEquals(initialScore, interviewState.getScore());
        assertEquals(1, interviewState.getCurrentIndex());
        assertFalse(interviewState.isFinished());
    }

    @Test
    void shouldMoveToNextQuestion_afterProcessingAnswer() {
        String answer = "1";
        
        assertEquals(0, interviewState.getCurrentIndex());
        assertEquals(currentQuestion, interviewState.getCurrentQuestion());
        
        questionService.processAnswer(interviewState, answer);
        
        assertEquals(1, interviewState.getCurrentIndex());
        assertNotEquals(currentQuestion, interviewState.getCurrentQuestion());
    }

    @Test
    void shouldFinishInterview_whenLastQuestionIsAnswered() {
        String answer = "1";
        
        assertEquals(0, interviewState.getCurrentIndex());
        assertFalse(interviewState.isFinished());
        
        questionService.processAnswer(interviewState, answer);
        
        assertEquals(1, interviewState.getCurrentIndex());
        assertFalse(interviewState.isFinished());
        
        questionService.processAnswer(interviewState, answer);
        
        assertEquals(2, interviewState.getCurrentIndex());
        assertTrue(interviewState.isFinished());
    }

    @Test
    void shouldPropagateValidationException_whenAnswerIsInvalid() {
        String invalidAnswer = "5";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> questionService.processAnswer(interviewState, invalidAnswer));
        
        assertEquals("Invalid answer selected", exception.getMessage());
        assertEquals("general", exception.getField());
        
        assertEquals(0, interviewState.getCurrentIndex());
        assertEquals(0, interviewState.getScore());
    }

    @Test
    void shouldHandleMultipleCorrectAnswers() {
        Question question1 = new Question(
                "Q1",
                List.of("A", "B", "C"),
                0
        );
        Question question2 = new Question(
                "Q2", 
                List.of("X", "Y", "Z"),
                2
        );
        
        InterviewState state = new InterviewState(
                Set.of(Topic.JUNIT),
                List.of(question1, question2)
        );
        
        questionService.processAnswer(state, "0");
        assertEquals(1, state.getScore());
        assertEquals(1, state.getCurrentIndex());
        
        questionService.processAnswer(state, "2");
        assertEquals(2, state.getScore());
        assertEquals(2, state.getCurrentIndex());
        assertTrue(state.isFinished());
    }

    @Test
    void shouldHandleAllIncorrectAnswers() {
        Question question1 = new Question(
                "Q1",
                List.of("A", "B", "C"),
                0
        );
        Question question2 = new Question(
                "Q2",
                List.of("X", "Y", "Z"),
                2
        );
        
        InterviewState state = new InterviewState(
                Set.of(Topic.JUNIT),
                List.of(question1, question2)
        );
        
        questionService.processAnswer(state, "1");
        assertEquals(0, state.getScore());
        assertEquals(1, state.getCurrentIndex());
        
        questionService.processAnswer(state, "1");
        assertEquals(0, state.getScore());
        assertEquals(2, state.getCurrentIndex());
        assertTrue(state.isFinished());
    }

    @Test
    void shouldHandleMixedCorrectAndIncorrectAnswers() {
        Question question1 = new Question(
                "Q1",
                List.of("A", "B", "C"),
                1
        );
        Question question2 = new Question(
                "Q2",
                List.of("X", "Y", "Z"),
                0
        );
        Question question3 = new Question(
                "Q3",
                List.of("P", "Q", "R"),
                2
        );
        
        InterviewState state = new InterviewState(
                Set.of(Topic.JUNIT),
                List.of(question1, question2, question3)
        );
        
        questionService.processAnswer(state, "1");
        assertEquals(1, state.getScore());
        assertEquals(1, state.getCurrentIndex());
        
        questionService.processAnswer(state, "1");
        assertEquals(1, state.getScore());
        assertEquals(2, state.getCurrentIndex());
        
        questionService.processAnswer(state, "2");
        assertEquals(2, state.getScore());
        assertEquals(3, state.getCurrentIndex());
        assertTrue(state.isFinished());
    }
}
