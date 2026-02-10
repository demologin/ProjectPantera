package com.javarush.trukhanova.service;

import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void testGetNextStep() {
        QuestStep step = gameService.getNextStep(1);
        assertNotNull(step);
        assertEquals(1, step.getId());
    }

    @Test
    void testIsGameOver() {
        QuestStep finalStep = new QuestStep();
        finalStep.setTitle("ПОБЕДА");
        finalStep.setAnswers(java.util.Collections.emptyList());

        assertTrue(gameService.isGameOver(finalStep), "Игра должна быть окончена, если нет ответов");
    }
}