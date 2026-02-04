package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.util.Link;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.javarush.vasileva.util.Link.LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogoutIT extends BaseIT {

    private final Logout logout = new Logout();

    @Test
    @DisplayName("when session exists then invalidate it and redirect to LOGIN")
    void whenSessionExists_ThenInvalidateAndRedirect() {
        when(req.getSession(false)).thenReturn(session);
        when(session.getId()).thenReturn("test-session-id");

        String redirect = logout.doGet(req);

        assertEquals(Link.LOGIN, redirect);
        verify(req).getSession(false);
        verify(session).invalidate();
    }

    @Test
    @DisplayName("when no session exists then skip invalidation and redirect to LOGIN")
    void whenNoSessionExists_ThenSkipInvalidationAndRedirect() {
        when(req.getSession(false)).thenReturn(null);

        String redirect = logout.doGet(req);

        assertEquals(LOGIN, redirect);
        verify(req).getSession(false);
        verify(session, never()).invalidate();
    }
}
