package com.javarush.golikov.quest.repository;

import com.javarush.golikov.quest.auth.Role;
import com.javarush.golikov.quest.model.User;
import org.junit.jupiter.api.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @BeforeEach
    void setUp() {
        UserRepository.clear();
    }

    @Test
    @DisplayName("Test save and find user")
    void testSaveAndFindUser() {

        User user = new User("test", "123", Role.USER);

        UserRepository.save(user);

        User found = UserRepository.find("test");

        assertNotNull(found);
        assertEquals("test", found.login());
        assertEquals("123", found.password());
        assertEquals(Role.USER, found.role());
    }

    @Test
    @DisplayName("Test find returns null for unknown user")
    void testFindUnknownUserReturnsNull() {

        User found = UserRepository.find("unknown");

        assertNull(found);
    }

    @Test
    @DisplayName("Test delete user")
    void testDeleteUser() {

        User user = new User("test", "123", Role.USER);
        UserRepository.save(user);

        UserRepository.delete("test");

        assertNull(UserRepository.find("test"));
    }

    @Test
    @DisplayName("Test all returns all saved users")
    void testAllReturnsUsers() {

        User user1 = new User("u1", "p1", Role.USER);
        User user2 = new User("u2", "p2", Role.ADMIN);

        UserRepository.save(user1);
        UserRepository.save(user2);

        Collection<User> users = UserRepository.all();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    @DisplayName("Test clear removes all users")
    void testClearRemovesAllUsers() {

        UserRepository.save(new User("u1", "p1", Role.USER));
        UserRepository.save(new User("u2", "p2", Role.ADMIN));

        UserRepository.clear();

        assertTrue(UserRepository.all().isEmpty());
    }
}