package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Answer;
import com.javarush.vasileva.repository.AnswerRepository;
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
public class AnswerServiceTest {
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    @DisplayName("when getAll() then return all answers from repository")
    void testGetAll() {
        List<Answer> answers = createMultipleAnswers();
        when(answerRepository.getAll()).thenReturn(answers);

        List<Answer> result = answerService.getAll();

        assertEquals(answers, result);
        verify(answerRepository).getAll();
    }

    @Test
    @DisplayName("when findById() then return answer by ID if it exists")
    void whenFindById_thenExisting() {
        Answer expected = createValidAnswer();
        when(answerRepository.findById(VALID_ANSWER_ID))
                .thenReturn(Optional.of(expected));

        Optional<Answer> result = answerService.findById(VALID_ANSWER_ID);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(answerRepository).findById(VALID_ANSWER_ID);
    }

    @Test
    @DisplayName("when findById() then return empty Optional if answer is not found")
    void whenFindById_ThenNonExisting() {
        when(answerRepository.findById(NON_EXISTENT_ANSWER_ID))
                .thenReturn(Optional.empty());

        Optional<Answer> result = answerService.findById(NON_EXISTENT_ANSWER_ID);

        assertFalse(result.isPresent());
        verify(answerRepository).findById(NON_EXISTENT_ANSWER_ID);
    }

    @Test
    @DisplayName("when create() then delegate saving answer to repository")
    void testCreate() {
        Answer newAnswer = createValidAnswer();
        answerService.create(newAnswer);
        verify(answerRepository).create(newAnswer);
    }
}
