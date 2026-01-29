package com.javarush.matsarskaya.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для UserNotFoundException")
class UserNotFoundExceptionTest {

    @Test
    @DisplayName("Создание исключения с именем пользователя")
    void testExceptionWithUsername() {
        UserNotFoundException exception = new UserNotFoundException("testuser");

        assertThat(exception.getMessage()).contains("testuser");
        assertThat(exception.getMessage()).contains("не найден");
    }

    @Test
    @DisplayName("Создание исключения с именем пользователя и причиной")
    void testExceptionWithUsernameAndCause() {
        Throwable cause = new RuntimeException("Database error");
        UserNotFoundException exception = new UserNotFoundException("testuser", cause);

        assertThat(exception.getMessage()).contains("testuser");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
