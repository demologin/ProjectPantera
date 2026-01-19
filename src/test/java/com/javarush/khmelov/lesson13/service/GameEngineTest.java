package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.GameResult;
import com.javarush.khmelov.lesson13.model.Scene;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    private final GameEngine engine = new GameEngine();

    @Test
    void startSceneExists() {
        Scene scene = engine.getScene("start");

        assertNotNull(scene);
        assertEquals("start", scene.getId());
    }

    @Test
    void winPathWorks() {
        GameResult step1 = engine.makeChoice("start", "yes");
        assertFalse(step1.isGameOver());

        GameResult step2 = engine.makeChoice(step1.getNextSceneId(), "go");
        assertFalse(step2.isGameOver());

        GameResult step3 = engine.makeChoice(step2.getNextSceneId(), "truth");
        assertTrue(step3.isGameOver());
        assertTrue(step3.isWin());
    }

    @Test
    void losePathWorks() {
        GameResult result = engine.makeChoice("start", "no");

        assertTrue(result.isGameOver());
        assertFalse(result.isWin());
    }

    @Test
    void wrongChoiceDoesNothing() {
        GameResult result = engine.makeChoice("start", "wrong");

        assertFalse(result.isGameOver());
        assertEquals("start", result.getNextSceneId());
    }
}