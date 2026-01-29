package com.javarush.matsarskaya.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для InvalidCredentialsException")
class InvalidCredentialsExceptionTest {

    @Test
    @DisplayName("Создание исключения без сообщения")
    void testExceptionWithoutMessage() {
        InvalidCredentialsException exception = new InvalidCredentialsException();

        assertThat(exception.getMessage()).contains("Неверное имя пользователя или пароль");
    }

    @Test
    @DisplayName("Создание исключения с сообщением")
    void testExceptionWithMessage() {
        String customMessage = "Custom error message";
        InvalidCredentialsException exception = new InvalidCredentialsException(customMessage);

        assertThat(exception.getMessage()).isEqualTo(customMessage);
    }
}
