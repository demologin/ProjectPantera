package com.javarush.matsarskaya.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для сущности User")
class UserTest {

    @Test
    @DisplayName("Создание пользователя с корректными данными")
    void testUserCreation() {
        User user = new User("testuser", "password123");
        
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Создание пользователя с пустым именем")
    void testUserWithEmptyUsername() {
        User user = new User("", "password123");
        
        assertThat(user.getUsername()).isEmpty();
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Создание пользователя с пустым паролем")
    void testUserWithEmptyPassword() {
        User user = new User("testuser", "");
        
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEmpty();
    }

    @Test
    @DisplayName("Геттеры возвращают корректные значения")
    void testGetters() {
        User user = new User("admin", "admin123");
        
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getPassword()).isEqualTo("admin123");
    }
}
