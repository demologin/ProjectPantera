package com.javarush.ushanov.repository;

import com.javarush.ushanov.entity.QuestStep;
import com.javarush.ushanov.entity.StepStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для QuestRepository.
 *
 * @BeforeEach — метод, который вызывается перед каждым тестом.
 *               Создаём свежий объект репозитория для каждого теста,
 *               чтобы тесты не влияли друг на друга.
 *
 * @Test       — помечает метод как тестовый.
 *
 * @DisplayName — человекочитаемое название теста.
 */
class QuestRepositoryTest {

    private QuestRepository questRepository;

    @BeforeEach
    void setUp() {
        questRepository = new QuestRepository();
    }

    @Test
    @DisplayName("Начальный шаг должен существовать")
    void startStepShouldExist() {
        Optional<QuestStep> startStep = questRepository.findById(QuestRepository.START_STEP_ID);

        // assertTrue: проверяем что условие истинно
        assertTrue(startStep.isPresent(), "Начальный шаг должен быть в репозитории");
    }

    @Test
    @DisplayName("Начальный шаг должен иметь статус PLAYING")
    void startStepShouldHavePlayingStatus() {
        QuestStep startStep = questRepository.findById(QuestRepository.START_STEP_ID).orElseThrow();

        // assertEquals: проверяем что ожидаемое значение == фактическому
        assertEquals(StepStatus.PLAYING, startStep.getStatus());
    }

    @Test
    @DisplayName("Начальный шаг должен иметь варианты ответов")
    void startStepShouldHaveOptions() {
        QuestStep startStep = questRepository.findById(QuestRepository.START_STEP_ID).orElseThrow();

        // assertFalse: проверяем что условие ложно
        assertFalse(startStep.getOptions().isEmpty(),
                "Начальный шаг должен содержать варианты ответов");
    }

    @Test
    @DisplayName("Несуществующий шаг должен возвращать пустой Optional")
    void nonExistentStepShouldReturnEmpty() {
        Optional<QuestStep> step = questRepository.findById(9999);

        assertFalse(step.isPresent(), "Несуществующий шаг должен быть пустым Optional");
    }

    @Test
    @DisplayName("В квесте должны быть финальные шаги с победой")
    void questShouldHaveWinSteps() {
        // Проверяем, что хотя бы один шаг имеет статус WIN
        boolean hasWinStep = false;
        for (int i = 1; i <= questRepository.size() + 5; i++) {
            Optional<QuestStep> step = questRepository.findById(i);
            if (step.isPresent() && step.get().getStatus() == StepStatus.WIN) {
                hasWinStep = true;
                break;
            }
        }
        assertTrue(hasWinStep, "В квесте должен быть хотя бы один шаг с победой");
    }

    @Test
    @DisplayName("В квесте должны быть финальные шаги с поражением")
    void questShouldHaveLoseSteps() {
        boolean hasLoseStep = false;
        for (int i = 1; i <= questRepository.size() + 5; i++) {
            Optional<QuestStep> step = questRepository.findById(i);
            if (step.isPresent() && step.get().getStatus() == StepStatus.LOSE) {
                hasLoseStep = true;
                break;
            }
        }
        assertTrue(hasLoseStep, "В квесте должен быть хотя бы один шаг с поражением");
    }

    @Test
    @DisplayName("Финальные шаги не должны иметь вариантов ответов")
    void finalStepsShouldHaveNoOptions() {
        for (int i = 1; i <= questRepository.size() + 5; i++) {
            Optional<QuestStep> optional = questRepository.findById(i);
            if (optional.isPresent()) {
                QuestStep step = optional.get();
                if (step.isCompleted()) {
                    // assertAll: проверяем несколько условий вместе
                    assertTrue(step.getOptions().isEmpty(),
                            "Финальный шаг id=" + step.getId() + " не должен иметь вариантов ответа");
                }
            }
        }
    }
}
