package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.User;
import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для LoginPage")
class LoginPageTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(userService);
    }

    @Test
    @DisplayName("GET запрос возвращает путь к странице входа")
    void testDoGet() {
        String result = loginPage.doGet(request);

        assertThat(result).isEqualTo("/WEB-INF/login-page.jsp");
    }

    @Test
    @DisplayName("Успешный вход пользователя")
    void testDoPostSuccess() {
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("password123");
        when(userService.loginUser("testuser", "password123"))
                .thenReturn(Optional.of(new User("testuser", "password123")));
        when(request.getSession()).thenReturn(session);

        String result = loginPage.doPost(request);

        assertThat(result).isEqualTo("/home-page");
        verify(userService).loginUser("testuser", "password123");
        verify(request).getSession();
        verify(session).setAttribute("username", "testuser");
    }

    @Test
    @DisplayName("Вход с несуществующим пользователем")
    void testDoPostUserNotFound() {
        when(request.getParameter("username")).thenReturn("nonexistent");
        when(request.getParameter("password")).thenReturn("password123");
        when(userService.loginUser("nonexistent", "password123"))
                .thenThrow(new UserNotFoundException("nonexistent"));

        String result = loginPage.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/login-page.jsp");
        verify(request).setAttribute("error", "Пользователь не найден");
        verify(userService).loginUser("nonexistent", "password123");
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    void testDoPostInvalidPassword() {
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(userService.loginUser("testuser", "wrongpassword"))
                .thenThrow(new InvalidCredentialsException());

        String result = loginPage.doPost(request);

        assertThat(result).isEqualTo("/WEB-INF/login-page.jsp");
        verify(request).setAttribute("error", "Неверное имя пользователя или пароль");
        verify(userService).loginUser("testuser", "wrongpassword");
    }

    @Test
    @DisplayName("Получение пути к представлению")
    void testGetView() {
        String result = loginPage.getView();

        assertThat(result).isEqualTo("/WEB-INF/login-page.jsp");
    }
}
