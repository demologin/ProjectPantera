package com.javarush.zyibin.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AvatarServiceTest {

    private final AvatarService avatarService = new AvatarService();

    @Test
    void shouldReturnNonEmptyListOfAvailableAvatars() {
        List<String> avatars = avatarService.getAvailableAvatars();

        assertNotNull(avatars, "Список аватаров не должен быть null");
        assertFalse(avatars.isEmpty(), "Список аватаров не должен быть пустым");
    }

    @Test
    void shouldContainExpectedAvatarPaths() {
        List<String> avatars = avatarService.getAvailableAvatars();

        assertTrue(avatars.contains("/resources/avatars/default/avatar1.png"));
        assertTrue(avatars.contains("/resources/avatars/default/avatar2.png"));
        assertTrue(avatars.contains("/resources/avatars/default/avatar3.png"));
        assertTrue(avatars.contains("/resources/avatars/default/avatar4.png"));
        assertTrue(avatars.contains("/resources/avatars/default/avatar5.png"));
    }
}

