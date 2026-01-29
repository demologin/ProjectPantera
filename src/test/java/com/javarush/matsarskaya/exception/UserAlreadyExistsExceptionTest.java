package com.javarush.matsarskaya.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для UserAlreadyExistsException")
class UserAlreadyExistsExceptionTest {

    @Test
    @DisplayName("Создание исключения с именем пользователя")
    void testExceptionWithUsername() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("testuser");

        assertThat(exception.getMessage()).contains("testuser");
        assertThat(exception.getMessage()).contains("уже существует");
    }

    @Test
    @DisplayName("Создание исключения с именем пользователя и причиной")
    void testExceptionWithUsernameAndCause() {
        Throwable cause = new RuntimeException("Database error");
        UserAlreadyExistsException exception = new UserAlreadyExistsException("testuser", cause);

        assertThat(exception.getMessage()).contains("testuser");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
