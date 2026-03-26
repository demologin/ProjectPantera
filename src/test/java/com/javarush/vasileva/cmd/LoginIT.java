package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.service.UserStatsService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Link;
import com.javarush.vasileva.util.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.javarush.vasileva.util.Key.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginIT extends BaseIT {

    private final UserService userService = Mockito.mock(UserService.class);
    private final UserStatsService userStatsService = Mockito.mock(UserStatsService.class);

    private final Login login = new Login(userService, userStatsService);

    @Test
    @DisplayName("when valid credentials then redirect to HOME and set session attributes")
    void whenValidCredentialsThenRedirectToHomeAndSetSessionAttributes() {
        when(req.getParameter(Key.EMAIL)).thenReturn(testAdmin.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testAdmin.getPassword());

        when(userService.login(testAdmin.getEmail(), testAdmin.getPassword()))
                .thenReturn(Optional.of(testAdmin));

        UserStats stats = new UserStats();
        stats.setUser(testAdmin);
        when(userStatsService.getUserStats(testAdmin)).thenReturn(Optional.of(stats));

        String redirect = login.doPost(req);

        assertEquals(Link.HOME, redirect);

        verify(session).setAttribute(USER, testAdmin);
        verify(session).setAttribute(Key.STATS, stats);

        verify(userService).login(testAdmin.getEmail(), testAdmin.getPassword());
        verify(userStatsService).getUserStats(testAdmin);
    }

    @Test
    @DisplayName("when invalid credentials then set error end return view")
    void whenInvalidCredentials_ThenSetErrorEndReturnView() {
        String invalidPassword = "wrongPassword";
        when(req.getParameter(Key.EMAIL)).thenReturn(testAdmin.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(invalidPassword);

        when(userService.login(testAdmin.getEmail(), invalidPassword))
                .thenReturn(Optional.empty());

        String redirect = login.doPost(req);

        assertEquals(login.getView(), redirect);
        verify(session).setAttribute(Key.ERROR, Value.INVALID_DATA_ERROR);
        verify(userService).login(testAdmin.getEmail(), invalidPassword);
        verifyNoMoreInteractions(userStatsService);
    }

    @Test
    @DisplayName("when empty email or password then set error and return view")
    void whenEmptyEmailOrPassword_ThenSetErrorAndReturnView() {
        String emptyEmail = "";
        String emptyPassword = "";
        when(req.getParameter(Key.EMAIL)).thenReturn(emptyEmail);
        when(req.getParameter(Key.PASSWORD)).thenReturn(emptyPassword);

        String redirect = login.doPost(req);

        assertEquals(login.getView(), redirect);
        verify(session).setAttribute(Key.ERROR, Value.EMPTY_DATA_ERROR);
        verifyNoInteractions(userService);
        verifyNoInteractions(userStatsService);
    }

    @Test
    @DisplayName("when user has no stats then create new stats")
    void whenUserHasNoStats_ThenCreateNewStats() {
        when(req.getParameter(Key.EMAIL)).thenReturn(testAdmin.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testAdmin.getPassword());

        when(userService.login(testAdmin.getEmail(), testAdmin.getPassword()))
                .thenReturn(Optional.of(testAdmin));

        UserStats stats = new UserStats();
        stats.setUser(testAdmin);
        when(userStatsService.getUserStats(testAdmin))
                .thenReturn(Optional.empty());
        when(userStatsService.createUserStats(testAdmin))
                .thenReturn(stats);

        String redirect = login.doPost(req);

        assertEquals(Link.HOME, redirect);
        verify(session).setAttribute(Key.USER, testAdmin);
        verify(session).setAttribute(Key.STATS, stats);
        verify(userStatsService).createUserStats(testAdmin);

    }


}
