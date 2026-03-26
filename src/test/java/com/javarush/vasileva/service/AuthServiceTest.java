package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.javarush.vasileva.service.TestData.*;
import static com.javarush.vasileva.util.Key.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("checkAdminAuthorization() должен пройти успешно для пользователя с ролью ADMIN")
    void testCheckAdminAuthorization_AdminSuccess() {
        User adminUser = createAdminUser();
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(adminUser);

        assertDoesNotThrow(() -> authService.checkAdminAuthorization(request, UNAUTHORIZED_MESSAGE));
        verify(request).getSession(false);
        verify(session).getAttribute(USER);
    }

    @Test
    @DisplayName("checkAdminAuthorization() должен выбросить AppException для null-пользователя")
    void testCheckAdminAuthorization_NullUserThrowsException() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(createNullUser());

        AppException exception = assertThrows(AppException.class,
                () -> authService.checkAdminAuthorization(request, UNAUTHORIZED_MESSAGE));

        assertEquals(UNAUTHORIZED_MESSAGE, exception.getMessage());
        verify(request).getSession(false);
        verify(session).getAttribute(USER);
    }

    @Test
    @DisplayName("checkAdminAuthorization() должен выбросить AppException для пользователя с ролью USER")
    void testCheckAdminAuthorization_UserRoleThrowsException() {
        User userUser = createUserUser();
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(userUser);

        AppException exception = assertThrows(AppException.class,
                () -> authService.checkAdminAuthorization(request, UNAUTHORIZED_MESSAGE));

        assertEquals(UNAUTHORIZED_MESSAGE, exception.getMessage());
        verify(request).getSession(false);
        verify(session).getAttribute(USER);
    }

    @Test
    @DisplayName("checkAdminAuthorization() должен выбросить AppException для пользователя с ролью GUEST")
    void testCheckAdminAuthorization_GuestRoleThrowsException() {
        User guestUser = createGuestUser();
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(guestUser);

        AppException exception = assertThrows(AppException.class,
                () -> authService.checkAdminAuthorization(request, UNAUTHORIZED_MESSAGE));

        assertEquals(UNAUTHORIZED_MESSAGE, exception.getMessage());
        verify(request).getSession(false);
        verify(session).getAttribute(USER);
    }

}
