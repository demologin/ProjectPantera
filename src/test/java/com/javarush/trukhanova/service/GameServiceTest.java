package com.javarush.trukhanova.service;

import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;
    private QuestRepository repository;

    @BeforeEach
    void setUp() {
        repository = new QuestRepository();
        gameService = new GameService(repository);
    }

    @Test
    @DisplayName("Проверка загрузки первого шага")
    void testGetFirstStep() {
        QuestStep step = gameService.getNextStep(1);
        assertNotNull(step, "Первый шаг не должен быть null");
        assertEquals(1, step.getId());
        assertEquals("Крушение", step.getTitle());
    }

    @Test
    @DisplayName("Проверка логики окончания игры (Победа)")
    void testIsGameOverWin() {
        QuestStep winStep = repository.getStep(7);
        assertTrue(gameService.isGameOver(winStep), "Шаг с победой должен завершать игру");
    }

    @Test
    @DisplayName("Проверка счетчика игр")
    void testGamesPlayedCounter() {
        assertEquals(0, gameService.getGamesPlayed());
        gameService.getNextStep(1);
        gameService.getNextStep(1);
        assertEquals(2, gameService.getGamesPlayed(), "Счетчик игр должен увеличиваться при выборе id=1");
    }
}