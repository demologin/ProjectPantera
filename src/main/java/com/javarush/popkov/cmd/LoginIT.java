package com.javarush.popkov.cmd;

import com.javarush.popkov.BaseIT;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.util.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginIT extends BaseIT {

    final Login login = Winter.find(Login.class);

    @Test
    @DisplayName("When login admin then redirect to profile")
    void whenLoginAdminThenRedirectToProfile() {
        when(request.getParameter("login")).thenReturn("Carl");
        when(request.getParameter("password")).thenReturn("admin");

        String actualRedirect = login.doPost(request);
        Assertions.assertEquals("/profile", actualRedirect);

        verify(session)
                .setAttribute(eq(Key.USER), any(User.class));
    }

    @Test
    @DisplayName("When incorrect login then redirect to login")
    void whenIncorrectLoginThenRedirectToLogin() {
        when(request.getParameter("login")).thenReturn("Carl");
        when(request.getParameter("password")).thenReturn("err");

        String actualRedirect = login.doPost(request);
        Assertions.assertEquals("/login", actualRedirect);
    }
}
