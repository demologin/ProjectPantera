package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для LogoutPage")
class LogoutPageTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    private LogoutPage logoutPage;

    @BeforeEach
    void setUp() {
        logoutPage = new LogoutPage(userService);
    }

    @Test
    @DisplayName("POST запрос выполняет выход из системы")
    void testDoPost() {
        doNothing().when(userService).logout(request);

        String result = logoutPage.doPost(request);

        assertThat(result).isEqualTo("/home-page");
        verify(userService).logout(request);
    }

    @Test
    @DisplayName("GET запрос вызывает doPost")
    void testDoGet() {
        doNothing().when(userService).logout(request);

        String result = logoutPage.doGet(request);

        assertThat(result).isEqualTo("/home-page");
        verify(userService).logout(request);
    }

    @Test
    @DisplayName("Получение пути к представлению")
    void testGetView() {
        String result = logoutPage.getView();

        assertThat(result).isEqualTo("/home-page");
    }
}
