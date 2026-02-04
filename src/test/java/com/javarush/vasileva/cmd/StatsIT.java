package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserStatsService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StatsIT extends BaseIT {

    private final UserStatsService statsService = mock(UserStatsService.class);

    private final Stats stats = new Stats(statsService);

    @Test
    @DisplayName("when user is authenticated then load stats and return view")
    void whenUserAuthenticated_ThenLoadStatsAndReturnView() {
        when(session.getAttribute(Key.USER)).thenReturn(testUser);
        when(statsService.getUserStats(testUser.getId())).thenReturn(Optional.of(testUserStats));

        String view = stats.doGet(req);

        assertEquals(stats.getView(), view);

        verify(req).getSession();
        verify(session).getAttribute(Key.USER);
        verify(statsService).getUserStats(testUser.getId());
        verify(req).setAttribute(eq(Key.STATS), eq(testUserStats));
    }

    @Test
    @DisplayName("when user not authenticated then throw AppException")
    void whenUserNotAuthenticated_ThenThrowAppException() {
        when(session.getAttribute(Key.USER)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> stats.doGet(req));

        assertEquals(Value.AUTH_ERROR, exception.getMessage());
        verify(req).getSession();
        verify(session).getAttribute(Key.USER);
        verify(statsService, never()).getUserStats(anyLong());
    }

    @Test
    @DisplayName("when stats not found then throw AppException")
    void whenStatsNotFound_ThenThrowAppException() {
        when(session.getAttribute(Key.USER)).thenReturn(testUser);
        when(statsService.getUserStats(testUser.getId())).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> stats.doGet(req));

        assertEquals(Value.STATS_NOT_FOUND, exception.getMessage());

        verify(req).getSession();
        verify(session).getAttribute(Key.USER);
        verify(statsService).getUserStats(testUser.getId());
        verify(req, never()).setAttribute(eq(Key.STATS), any());
    }

}
