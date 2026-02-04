package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.repository.UserStatsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javarush.vasileva.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserStatsServiceTest {

    @Mock
    private UserStatsRepository userStatsRepository;

    @InjectMocks
    private UserStatsService userStatsService;

    @Test
    @DisplayName("when createUserStats() then delegate stats creation to repository")
    void testCreateUserStats() {
        UserStats expectedStats = createDefaultUserStats();
        when(userStatsRepository.createUserStats(VALID_USER_ID))
                .thenReturn(expectedStats);

        UserStats result = userStatsService.createUserStats(VALID_USER_ID);

        assertEquals(expectedStats, result);
        verify(userStatsRepository).createUserStats(VALID_USER_ID);
    }

    @Test
    @DisplayName("when getUserStats() then return user stats if it exists")
    void whenGetUserStats_thenExisting() {
        UserStats expectedStats = createFilledUserStats();
        when(userStatsRepository.getUserStats(VALID_USER_ID))
                .thenReturn(Optional.of(expectedStats));

        Optional<UserStats> result = userStatsService.getUserStats(VALID_USER_ID);

        assertTrue(result.isPresent());
        assertEquals(expectedStats, result.get());
        verify(userStatsRepository).getUserStats(VALID_USER_ID);
    }

    @Test
    @DisplayName("when getUserStats() then return empty Optional if stats is not found")
    void whenGetUserStats_ThenNonExisting() {
        when(userStatsRepository.getUserStats(NON_EXISTENT_USER_ID))
                .thenReturn(Optional.empty());

        Optional<UserStats> result = userStatsService.getUserStats(NON_EXISTENT_USER_ID);

        assertFalse(result.isPresent());
        verify(userStatsRepository).getUserStats(NON_EXISTENT_USER_ID);
    }

    @Test
    @DisplayName("when updateUserStats() then increase  total и wins if WIN-question")
    void testUpdateUserStats_WinQuestion() {
        UserStats stats = createDefaultUserStats();
        Question winQuestion = createWinQuestion();

        doAnswer(invocation -> {
            invocation.getArgument(0);
            return null;
        }).when(userStatsRepository).updateUserStats(any(UserStats.class));

        userStatsService.updateUserStats(winQuestion, stats);

        assertEquals(1, stats.getTotal());
        assertEquals(1, stats.getWins());
        assertEquals(0, stats.getLosses());
        verify(userStatsRepository).updateUserStats(stats);
    }

    @Test
    @DisplayName("when updateUserStats() then increase total и losses if LOSS-question")
    void testUpdateUserStats_LossQuestion() {
        UserStats stats = createDefaultUserStats();
        Question lossQuestion = createLossQuestion();

        doAnswer(invocation -> null)
                .when(userStatsRepository).updateUserStats(any(UserStats.class));

        userStatsService.updateUserStats(lossQuestion, stats);

        assertEquals(1, stats.getTotal());
        assertEquals(0, stats.getWins());
        assertEquals(1, stats.getLosses());
        verify(userStatsRepository).updateUserStats(stats);
    }
}
