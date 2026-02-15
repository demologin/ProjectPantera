package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Game;
import com.javarush.goncharov.model.GameState;
import com.javarush.goncharov.model.Statistic;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {
    @Mock
    GameRepository gameRepository;
    @InjectMocks
    StatisticService statisticService;

    User user1;
    Game winGame;
    Game loseGame;
    Game playGame;
    final Long TEST_USER_ID = 2L;

    @BeforeEach
    void setup() {
        user1 = User.builder().id(TEST_USER_ID).login("Dima").build();
        winGame = Game.builder()
                .id(1L)
                .userId(user1.getId())
                .gameState(GameState.WIN)
                .build();
        loseGame = Game.builder()
                .id(2L)
                .userId(user1.getId())
                .gameState(GameState.LOSE)
                .build();
        playGame = Game.builder()
                .id(3L)
                .userId(user1.getId())
                .gameState(GameState.PLAY)
                .build();
    }

    @Test
    @DisplayName("Get user statistic")
    void getUserStatistic() {
        List<Game> games = List.of(winGame, winGame, loseGame, playGame);
        when(gameRepository.findByUserId(TEST_USER_ID)).thenReturn(games.stream());

        Statistic result = statisticService.getUserStat(user1);

        assertEquals("Dima", result.getLogin());
        assertEquals(2, result.getWin());
        assertEquals(1, result.getLost());
        assertEquals(1, result.getPlay());
        assertEquals(4, result.getTotal());
    }
}