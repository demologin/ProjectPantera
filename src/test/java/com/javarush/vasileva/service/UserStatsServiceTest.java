package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.entity.User;
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
        User user = createValidUser();
        UserStats expectedStats = createDefaultUserStats();
        when(userStatsRepository.createUserStats(eq(user)))
                .thenReturn(expectedStats);

        UserStats result = userStatsService.createUserStats(user);

        assertEquals(expectedStats, result);
        verify(userStatsRepository).createUserStats(eq(user));
    }

    @Test
    @DisplayName("when getUserStats() then return user stats if it exists")
    void whenGetUserStats_thenExisting() {
        User user = createValidUser();
        UserStats expectedStats = createFilledUserStats();
        when(userStatsRepository.getUserStats(eq(user)))
                .thenReturn(Optional.of(expectedStats));

        Optional<UserStats> result = userStatsService.getUserStats(user);

        assertTrue(result.isPresent());
        assertEquals(expectedStats, result.get());
        verify(userStatsRepository).getUserStats(eq(user));
    }

    @Test
    @DisplayName("when getUserStats() then return empty Optional if stats is not found")
    void whenGetUserStats_ThenNonExisting() {
        when(userStatsRepository.getUserStats(createNullUser()))
                .thenReturn(Optional.empty());

        Optional<UserStats> result = userStatsService.getUserStats(createNullUser());

        assertFalse(result.isPresent());
        verify(userStatsRepository).getUserStats(createNullUser());
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
