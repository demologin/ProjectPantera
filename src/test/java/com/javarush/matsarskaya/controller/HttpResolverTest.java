package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import com.javarush.matsarskaya.cmd.HomePage;
import com.javarush.matsarskaya.cmd.LoginPage;
import com.javarush.matsarskaya.cmd.QuestDragon;
import com.javarush.matsarskaya.cmd.RegisterPage;
import com.javarush.matsarskaya.cmd.StatisticPage;
import com.javarush.matsarskaya.cmd.LogoutPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для HttpResolver")
class HttpResolverTest {
    private HttpResolver httpResolver;

    @BeforeEach
    void setUp() {
        httpResolver = new HttpResolver();
    }

    @Test
    @DisplayName("Разрешение команды для домашней страницы")
    void testResolveHomePage() {
        Command command = httpResolver.resolve("/home-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(HomePage.class);
    }

    @Test
    @DisplayName("Разрешение команды для страницы квеста")
    void testResolveQuestDragon() {
        Command command = httpResolver.resolve("/quest-dragon");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(QuestDragon.class);
    }

    @Test
    @DisplayName("Разрешение команды для страницы входа")
    void testResolveLoginPage() {
        Command command = httpResolver.resolve("/login-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(LoginPage.class);
    }

    @Test
    @DisplayName("Разрешение команды для страницы регистрации")
    void testResolveRegisterPage() {
        Command command = httpResolver.resolve("/register-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(RegisterPage.class);
    }

    @Test
    @DisplayName("Разрешение команды для выхода")
    void testResolveLogout() {
        Command command = httpResolver.resolve("/logout");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(LogoutPage.class);
    }

    @Test
    @DisplayName("Разрешение команды для страницы статистики")
    void testResolveStatisticPage() {
        Command command = httpResolver.resolve("/statistic-page");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(StatisticPage.class);
    }

    @Test
    @DisplayName("Разрешение неизвестного пути возвращает домашнюю страницу")
    void testResolveUnknownPath() {
        Command command = httpResolver.resolve("/unknown-path");

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(HomePage.class);
    }
}
