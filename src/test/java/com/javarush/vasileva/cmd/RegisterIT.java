package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Link;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.javarush.vasileva.util.Value.EMPTY_DATA_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegisterIT extends BaseIT {

    private final UserService userService = mock(UserService.class);

    private final Register register = new Register(userService);

    @Test
    @DisplayName("when valid data then register user and redirect to login")
    void whenValidData_ThenRegisterUserAndRedirectToLogin() {

        when(req.getParameter(com.javarush.vasileva.util.Key.LOGIN)).thenReturn(testGuest.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(testGuest.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testGuest.getPassword());

        String redirect = register.doPost(req);

        assertEquals(Link.LOGIN, redirect);
        verify(userService).register(testGuest.getLogin(), testGuest.getEmail(), testGuest.getPassword());
    }

    @Test
    @DisplayName("when empty login then throw AppException")
    void whenEmptyLogin_ThenThrowAppException() {
        String emptyLogin = "";
        when(req.getParameter(Key.LOGIN)).thenReturn(emptyLogin);
        when(req.getParameter(Key.EMAIL)).thenReturn(testGuest.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testGuest.getPassword());

        AppException exception = assertThrows(AppException.class, () -> register.doPost(req));

        assertEquals(EMPTY_DATA_ERROR, exception.getMessage());
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("when empty email then throw AppException")
    void whenEmptyEmailThenThrowAppException() {
        String emptyEmail = "";
        when(req.getParameter(Key.LOGIN)).thenReturn(testGuest.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(emptyEmail);
        when(req.getParameter(Key.PASSWORD)).thenReturn(testGuest.getPassword());

        AppException exception = assertThrows(AppException.class, () -> register.doPost(req));

        assertEquals(EMPTY_DATA_ERROR, exception.getMessage());
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("when empty password then throw AppException")
    void whenEmptyPasswordThenThrowAppException() {
        String emptyPassword = "";
        when(req.getParameter(Key.LOGIN)).thenReturn(testGuest.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(testGuest.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(emptyPassword);

        AppException exception = assertThrows(AppException.class, () -> register.doPost(req));

        assertEquals(EMPTY_DATA_ERROR, exception.getMessage());
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("when null login then throw AppException")
    void whenNullLogin_ThenThrowAppException() {
        when(req.getParameter(Key.LOGIN)).thenReturn(null);
        when(req.getParameter(Key.EMAIL)).thenReturn(testGuest.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testGuest.getPassword());

        AppException exception = assertThrows(AppException.class, () -> register.doPost(req));

        assertEquals(EMPTY_DATA_ERROR, exception.getMessage());
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("when null email then throw AppException")
    void whenNullEmail_ThenThrowAppException() {
        when(req.getParameter(Key.LOGIN)).thenReturn(testGuest.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(null);
        when(req.getParameter(Key.PASSWORD)).thenReturn(testGuest.getPassword());

        AppException exception = assertThrows(AppException.class, () -> register.doPost(req));

        assertEquals(EMPTY_DATA_ERROR, exception.getMessage());
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("when null password then throw AppException")
    void whenNullPasswordThenThrowAppException() {
        when(req.getParameter(Key.LOGIN)).thenReturn(testGuest.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(testGuest.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> register.doPost(req));

        assertEquals(EMPTY_DATA_ERROR, exception.getMessage());
        verifyNoInteractions(userService);
    }
}
