package com.javarush.trukhanova.service;

import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.entity.Answer;
import com.javarush.trukhanova.exception.StepNotFoundException;
import com.javarush.trukhanova.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private GameService gameService;

    @Mock
    private QuestRepository repository;

    @BeforeEach
    void setUp() {
        gameService = new GameService(repository);
    }

    @Test
    @DisplayName("Должен возвращать корректный шаг из репозитория")
    void shouldReturnCorrectStepFromRepository() {
        QuestStep mockStep = new QuestStep(1, "Начало", "Описаниe", "img.png", Collections.emptyList());
        when(repository.getById(1)).thenReturn(mockStep);

        QuestStep result = gameService.getNextStep(1);

        assertNotNull(result);
        assertEquals("Начало", result.getTitle());
        verify(repository).getById(1);
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при ошибке репозитория")
    void shouldThrowExceptionWhenRepositoryFails() {
        when(repository.getById(anyInt())).thenThrow(new StepNotFoundException("Шаг не найден"));

        assertThrows(StepNotFoundException.class, () -> gameService.getNextStep(999));
    }

    @Test
    @DisplayName("Должен подтверждать окончание игры (нет ответов)")
    void shouldIdentifyGameOver() {
        QuestStep finalStep = new QuestStep();
        finalStep.setTitle("Финал");
        finalStep.setAnswers(Collections.emptyList());

        assertTrue(gameService.isGameOver(finalStep));
    }

    @Test
    @DisplayName("Должен понимать, что игра продолжается (есть ответы)")
    void shouldIdentifyThatGameContinues() {
        QuestStep step = new QuestStep();
        step.setTitle("Обычный шаг");
        step.setAnswers(List.of(new Answer("Вперед", 2)));

        assertFalse(gameService.isGameOver(step));
    }

    @Test
    @DisplayName("Безопасная проверка на null в списке ответов")
    void shouldHandleNullAnswersList() {
        QuestStep step = new QuestStep();
        step.setTitle("Технический шаг");
        step.setAnswers(null);

        assertTrue(gameService.isGameOver(step), "Если списка ответов нет (null), игра должна считаться оконченной");
    }
}