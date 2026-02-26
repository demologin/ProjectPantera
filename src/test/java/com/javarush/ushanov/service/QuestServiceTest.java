package com.javarush.ushanov.service;

import com.javarush.ushanov.entity.QuestStep;
import com.javarush.ushanov.entity.StepStatus;
import com.javarush.ushanov.exception.QuestStepNotFoundException;
import com.javarush.ushanov.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для QuestService.
 *
 * Здесь мы тестируем бизнес-логику:
 * - корректно ли сервис возвращает шаги
 * - правильно ли обрабатывает переходы между шагами
 * - корректно ли бросает исключения
 */
class QuestServiceTest {

    private QuestService questService;

    @BeforeEach
    void setUp() {
        // Создаём настоящий репозиторий — в нём уже есть все шаги квеста
        questService = new QuestService(new QuestRepository());
    }

    @Test
    @DisplayName("getStep() должен вернуть шаг по существующему id")
    void getStepShouldReturnStepById() {
        QuestStep step = questService.getStep(1);

        assertNotNull(step, "Шаг не должен быть null");
        assertEquals(1, step.getId(), "Id шага должен совпадать");
    }

    @Test
    @DisplayName("getStep() должен бросить исключение для несуществующего id")
    void getStepShouldThrowExceptionForInvalidId() {
        // assertThrows: проверяем, что метод бросает конкретное исключение
        assertThrows(QuestStepNotFoundException.class, () -> questService.getStep(9999),
                "Должно быть брошено QuestStepNotFoundException");
    }

    @Test
    @DisplayName("getStartStepId() должен возвращать корректный id")
    void getStartStepIdShouldReturnValidId() {
        int startId = questService.getStartStepId();

        // Проверяем, что стартовый шаг реально существует
        assertDoesNotThrow(() -> questService.getStep(startId),
                "Стартовый шаг должен существовать в репозитории");
    }

    @Test
    @DisplayName("getNextStepId() должен вернуть id следующего шага")
    void getNextStepIdShouldReturnCorrectNextStep() {
        // Берём первый шаг и смотрим какие у него варианты
        QuestStep startStep = questService.getStep(1);
        assertFalse(startStep.getOptions().isEmpty(), "Первый шаг должен иметь варианты");

        // Берём первый доступный вариант
        String firstOption = startStep.getOptions().keySet().iterator().next();
        int expectedNextId = startStep.getOptions().get(firstOption);

        // Проверяем, что сервис возвращает правильный следующий шаг
        int actualNextId = questService.getNextStepId(1, firstOption);
        assertEquals(expectedNextId, actualNextId);
    }

    @Test
    @DisplayName("getNextStepId() должен бросить исключение для несуществующего варианта ответа")
    void getNextStepIdShouldThrowExceptionForInvalidOption() {
        assertThrows(QuestStepNotFoundException.class,
                () -> questService.getNextStepId(1, "несуществующий вариант"),
                "Должно быть брошено исключение для несуществующего варианта");
    }

    @Test
    @DisplayName("Путь к победе должен существовать и завершаться WIN")
    void winPathShouldExistAndEndWithWin() {
        // Шаг 1 -> выбираем первый вариант (принять сигнал) -> шаг 2
        // Шаг 2 -> выбираем первый вариант (пристыковаться) -> шаг 4
        // Шаг 4 -> вариант "Найти выживших" -> шаг 7
        // Шаг 7 -> вариант "Забрать обоих" -> шаг 8 (WIN)

        int nextId = questService.getNextStepId(1, "Принять сигнал и изменить курс");
        nextId = questService.getNextStepId(nextId, "Пристыковаться и войти на станцию");
        nextId = questService.getNextStepId(nextId, "Найти выживших в жилом модуле");
        nextId = questService.getNextStepId(nextId, "Забрать обоих — найдём способ");

        QuestStep finalStep = questService.getStep(nextId);
        assertEquals(StepStatus.WIN, finalStep.getStatus(),
                "Финальный шаг победного пути должен иметь статус WIN");
    }

    @Test
    @DisplayName("Путь к поражению должен существовать и завершаться LOSE")
    void losePathShouldExistAndEndWithLose() {
        // Шаг 1 -> игнорировать сигнал -> шаг 3 (LOSE)
        int nextId = questService.getNextStepId(1, "Игнорировать — миссия важнее");

        QuestStep finalStep = questService.getStep(nextId);
        assertEquals(StepStatus.LOSE, finalStep.getStatus(),
                "Финальный шаг этого пути должен иметь статус LOSE");
    }
}
