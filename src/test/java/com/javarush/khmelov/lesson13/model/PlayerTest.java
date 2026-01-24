package com.javarush.khmelov.lesson13.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void winIncrementsStats() {
        Player p = new Player("test");

        p.recordWin();

        assertEquals(1, p.getGamesPlayed());
        assertEquals(1, p.getWins());
        assertEquals(0, p.getLosses());
    }
}
