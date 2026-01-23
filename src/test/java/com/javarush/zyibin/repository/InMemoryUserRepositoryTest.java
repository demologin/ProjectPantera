package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    private User createUser(String username) {
        return new User(
                0L,
                username,
                "hash",
                username + "@mail.com",
                Role.USER
        );
    }

    @Test
    void shouldAssignIdWhenSavingUserWithoutId() {

        User user = createUser("john");

        repository.save(user);

        assertTrue(user.getId() > 0);
    }

    @Test
    void shouldNotOverrideExistingIdWhenSavingUser() {

        User user = createUser("john");
        user.setId(42L);

        repository.save(user);

        assertEquals(42L, user.getId());
    }

    @Test
    void shouldFindUserById() {

        User user = createUser("john");
        repository.save(user);
        long id = user.getId();

        Optional<User> found = repository.findById(id);

        assertTrue(found.isPresent());
        assertEquals("john", found.get().getUsername());
    }

    @Test
    void shouldFindUserByUsername() {

        User user = createUser("john");
        repository.save(user);

        Optional<User> found = repository.findByUserName("john");

        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
    }

    @Test
    void shouldReturnEmptyOptionalWhenUserNotFoundByUsername() {

        Optional<User> found = repository.findByUserName("unknown");

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldReturnAllSavedUsers() {

        repository.save(createUser("john"));
        repository.save(createUser("mary"));
        repository.save(createUser("alex"));

        List<User> users = repository.findAll();

        assertEquals(3, users.size());
    }
}
