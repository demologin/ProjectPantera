package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.javarush.vasileva.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("when create() then delegate to repository")
    void testCreate() {
        Question question = createSimpleQuestion();
        questionService.create(question);
        verify(questionRepository).create(question);
    }

    @Test
    @DisplayName("when getAll() then return all questions from repository")
    void testGetAll() {
        List<Question> questions = createMultipleQuestions();
        when(questionRepository.getAll()).thenReturn(questions);

        List<Question> result = questionService.getAll();

        assertEquals(questions, result);
        verify(questionRepository).getAll();
    }

    @Test
    @DisplayName("when findById() then return question by ID")
    void testFindById() {
        Question expected = createQuestionWithAnswers();
        when(questionRepository.findById(VALID_QUESTION_ID))
                .thenReturn(Optional.of(expected));

        Optional<Question> result = questionService.findById(VALID_QUESTION_ID);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(questionRepository).findById(VALID_QUESTION_ID);
    }

    @Test
    @DisplayName("when findById() then return empty Optional if question not found")
    void whenFindById_ThenQuestionNotFound() {
        when(questionRepository.findById(NON_EXISTENT_QUESTION_ID))
                .thenReturn(Optional.empty());

        Optional<Question> result = questionService.findById(NON_EXISTENT_QUESTION_ID);

        assertFalse(result.isPresent());
        verify(questionRepository).findById(NON_EXISTENT_QUESTION_ID);
    }

    @Test
    @DisplayName("when getByQuestionLabelAndQuestId() then return question by label and quest ID")
    void testGetByQuestionLabelAndQuestId() {
        Question expected = createQuestionWithAnswers();
        when(questionRepository.getByQuestionLabelAndQuestId(
                VALID_LABEL, VALID_QUEST_ID))
                .thenReturn(Optional.of(expected));

        Optional<Question> result = questionService.getByQuestionLabelAndQuestId(
                VALID_LABEL, VALID_QUEST_ID);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(questionRepository).getByQuestionLabelAndQuestId(
                VALID_LABEL, VALID_QUEST_ID);
    }

    @Test
    @DisplayName("when getByQuestionLabelAndQuestId() then return empty if not found")
    void whenGetByQuestionLabelAndQuestId_ThenQuestionNotFound() {
        when(questionRepository.getByQuestionLabelAndQuestId(
                INVALID_LABEL, VALID_QUEST_ID))
                .thenReturn(Optional.empty());

        Optional<Question> result = questionService.getByQuestionLabelAndQuestId(
                INVALID_LABEL, VALID_QUEST_ID);

        assertFalse(result.isPresent());
        verify(questionRepository).getByQuestionLabelAndQuestId(
                INVALID_LABEL, VALID_QUEST_ID);
    }

    @Test
    @DisplayName("when isFinalQuestion() then return true for question with null answers")
    void whenIsFinalQuestion_ThenNullAnswers() {
        Question question = createFinalQuestion();

        boolean result = questionService.isFinalQuestion(question);

        assertTrue(result);
    }

    @Test
    @DisplayName("when isFinalQuestion() then return true for question with empty answers list")
    void whenIsFinalQuestion_EmptyAnswers() {
        Question question = createSimpleQuestion();
        question.setAnswers(List.of());

        boolean result = questionService.isFinalQuestion(question);

        assertTrue(result, "Question is final if answer list is empty");
        assertEquals(List.of(), question.getAnswers());
    }

    @Test
    @DisplayName("when isFinalQuestion() then return false for question with non‑empty answers list")
    void whenIsNotFinalQuestion_thenNonEmptyAnswers() {
        Question question = createQuestionWithAnswers();
        assertFalse(question.getAnswers().isEmpty());

        boolean result = questionService.isFinalQuestion(question);

        assertFalse(result, "Question is not final if it has answers");
    }
}
