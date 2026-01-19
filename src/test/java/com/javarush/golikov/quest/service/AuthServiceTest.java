package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @Test
    @DisplayName("Test login returns user when credentials are correct")
    void testLoginReturnsUserWhenCredentialsAreCorrect() {

        User user = new User("admin", "123", null);

        try (MockedStatic<UserRepository> repoMock = mockStatic(UserRepository.class)) {
            repoMock.when(() -> UserRepository.find("admin"))
                    .thenReturn(user);

            User result = authService.login("admin", "123");

            assertNotNull(result);
            assertEquals("admin", result.login());
        }
    }

    @Test
    @DisplayName("Test login returns null when password is incorrect")
    void testLoginReturnsNullWhenPasswordIsIncorrect() {

        User user = new User("admin", "123", null);

        try (MockedStatic<UserRepository> repoMock = mockStatic(UserRepository.class)) {
            repoMock.when(() -> UserRepository.find("admin"))
                    .thenReturn(user);

            User result = authService.login("admin", "wrong");

            assertNull(result);
        }
    }

    @Test
    @DisplayName("Test login returns null when user does not exist")
    void testLoginReturnsNullWhenUserDoesNotExist() {

        try (MockedStatic<UserRepository> repoMock = mockStatic(UserRepository.class)) {
            repoMock.when(() -> UserRepository.find("ghost"))
                    .thenReturn(null);

            User result = authService.login("ghost", "123");

            assertNull(result);
        }
    }

    @Test
    @DisplayName("Test logout invalidates session")
    void testLogoutInvalidatesSession() {

        HttpSession session = mock(HttpSession.class);

        AuthService service = new AuthService();
        service.logout(session);

        verify(session).invalidate();
    }

    @Test
    void testLoginUserNotFound() {

        try (MockedStatic<UserRepository> repo = mockStatic(UserRepository.class)) {
            repo.when(() -> UserRepository.find("u"))
                    .thenReturn(null);

            AuthService service = new AuthService();
            User result = service.login("u", "p");

            assertNull(result);
        }
    }

    @Test
    void testLoginWrongPassword() {

        User user = new User("u", "correct", null);

        try (MockedStatic<UserRepository> repo = mockStatic(UserRepository.class)) {
            repo.when(() -> UserRepository.find("u"))
                    .thenReturn(user);

            AuthService service = new AuthService();
            User result = service.login("u", "wrong");

            assertNull(result);
        }
    }

    @Test
    void testLoginReturnsNullWhenUserExistsButPasswordNullSafe() {

        User user = new User("u", "pass", null);

        try (MockedStatic<UserRepository> repo = mockStatic(UserRepository.class)) {
            repo.when(() -> UserRepository.find("u"))
                    .thenReturn(user);

            AuthService service = new AuthService();
            User result = service.login("u", "pass2");

            // важно: именно return null ПОСЛЕ if
            assertNull(result);
        }
    }

    @Test
    @DisplayName("Integration test login without static mocking")
    void testLoginWithoutMockStatic() {

        // реальный пользователь в репозитории
        UserRepository.clear();
        UserRepository.save(new User("real", "123", null));

        AuthService service = new AuthService();

        User result = service.login("real", "123");

        assertNotNull(result);
    }
}