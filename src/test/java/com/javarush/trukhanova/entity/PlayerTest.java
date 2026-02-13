package com.javarush.trukhanova.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("Проверка создания игрока и инкремента счетчика")
    void shouldCreatePlayerAndIncrementGames() {
        Player player = new Player("Герой", "static/images/avatars/1.png");

        assertAll("Инициализация игрока",
                () -> assertEquals("Герой", player.getName()),
                () -> assertEquals("static/images/avatars/1.png", player.getAvatarPath()),
                () -> assertEquals(0, player.getGamesPlayed(), "На старте должно быть 0 игр")
        );

        player.incrementGamesPlayed();
        assertEquals(1, player.getGamesPlayed(), "Счетчик должен стать равным 1");
    }
}