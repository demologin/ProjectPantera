package com.javarush.alimov.quest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    @Test
    void testVictoryPath() {
        GameResult result = GameLogic.next(GameState.START, null);
        result = GameLogic.next(result.state(), "accept");
        result = GameLogic.next(result.state(), "go");
        result = GameLogic.next(result.state(), "truth");

        assertEquals(GameState.WIN, result.state());
        assertEquals("Тебя вернули домой. Победа!", result.message());
    }

    @Test
    void testDefeatByDecline() {
        GameResult result = GameLogic.next(GameState.START, null);
        result = GameLogic.next(result.state(), "decline");

        assertEquals(GameState.LOSE, result.state());
        assertEquals("Ты отклонил вызов. Поражение.", result.message());
    }

    @Test
    void testDefeatByRefuseBridge() {
        GameResult result = GameLogic.next(GameState.START, null);
        result = GameLogic.next(result.state(), "accept");
        result = GameLogic.next(result.state(), "refuse");

        assertEquals(GameState.LOSE, result.state());
        assertEquals("Ты не пошёл на переговоры. Поражение.", result.message());
    }

    @Test
    void testDefeatByLie() {
        GameResult result = GameLogic.next(GameState.START, null);
        result = GameLogic.next(result.state(), "accept");
        result = GameLogic.next(result.state(), "go");
        result = GameLogic.next(result.state(), "lie");

        assertEquals(GameState.LOSE, result.state());
        assertEquals("Твою ложь разоблачили. Поражение.", result.message());
    }
}

