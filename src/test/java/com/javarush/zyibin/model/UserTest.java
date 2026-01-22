package com.javarush.zyibin.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldCreateUserWithDefaultValues() {

        User user = new User(
                1L,
                "john",
                "hash",
                "john@mail.com",
                Role.USER
        );

        assertEquals(1L, user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("hash", user.getPasswordHash());
        assertEquals("john@mail.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());

        assertEquals("john", user.getNickname());
        assertEquals("", user.getAbout());
        assertEquals("/avatars/default/default.png", user.getAvatarPath());

        assertFalse(user.isBlocked());
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void shouldAllowChangingProfileFields() {

        User user = new User(
                1L,
                "john",
                "hash",
                "john@mail.com",
                Role.USER
        );

        user.setNickname("Johnny");
        user.setAbout("Java developer");
        user.setAvatarPath("/avatars/custom/john.png");

        assertEquals("Johnny", user.getNickname());
        assertEquals("Java developer", user.getAbout());
        assertEquals("/avatars/custom/john.png", user.getAvatarPath());
    }

    @Test
    void shouldBlockAndUnblockUser() {

        User user = new User(
                1L,
                "john",
                "hash",
                "john@mail.com",
                Role.USER
        );

        user.setBlocked(true);

        assertTrue(user.isBlocked());

        user.setBlocked(false);

        assertFalse(user.isBlocked());
    }

    @Test
    void shouldChangeUserRole() {

        User user = new User(
                1L,
                "john",
                "hash",
                "john@mail.com",
                Role.USER
        );

        user.changeRole(Role.ADMIN);

        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void shouldAllowSettingIdOnceWhenInitialIdIsZero() {

        User user = new User(
                0L,
                "john",
                "hash",
                "john@mail.com",
                Role.USER
        );

        user.setId(10L);

        assertEquals(10L, user.getId());
    }

    @Test
    void shouldThrowExceptionWhenSettingIdSecondTime() {

        User user = new User(
                1L,
                "john",
                "hash",
                "john@mail.com",
                Role.USER
        );

        assertThrows(
                IllegalStateException.class,
                () -> user.setId(2L)
        );
    }
}
