package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class PlayerServiceTest {

    private PlayerService service;

    @BeforeEach
    void setUp() {
        service = PlayerService.getInstance();
        service.clear();
    }

    @Test
    void registerCreatesNewPlayer() {
        Player p = service.register("hero", "123");
        assertEquals("hero", p.getLogin());
    }

    @Test
    void registerWithExistingLoginThrows() {
        service.register("hero", "123");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register("hero", "456")
        );
    }

    @Test
    void loginWithCorrectPasswordWorks() {
        service.register("hero", "123");

        Player p = service.login("hero", "123");
        assertEquals("hero", p.getLogin());
    }

    @Test
    void loginWithWrongPasswordThrows() {
        service.register("hero", "123");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.login("hero", "wrong")
        );
    }

    @Test
    void loginOfMissingPlayerThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.login("ghost", "123")
        );
    }
}
