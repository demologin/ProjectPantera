package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Game;
import com.javarush.vasileva.entity.GameState;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.game.GameEngine;
import com.javarush.vasileva.repository.InMemoryGameRepository;
import com.javarush.vasileva.util.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javarush.vasileva.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private InMemoryGameRepository gameRepository;
    @Mock
    private UserService userService;
    @Mock
    private QuestService questService;
    @Mock
    private GameEngine gameEngine;

    @InjectMocks
    private GameService gameService;

    private User user;
    private Quest quest;

    @BeforeEach
    public void setUp() {
        user = createValidUser();
        quest = createValidQuest();
    }

    @Test
    @DisplayName("when startNewGame() then create and save new game when user and quest exist")
    void whenStartNewGame_ThenSuccess() {
        GameState initialState = createInitialGameState();
        Game expectedGame = createGame(
                initialState.getCurrentQuestion().getGeneratedId(),
                initialState
        );

        when(userService.findById(VALID_USER_ID)).thenReturn(Optional.of(user));
        when(questService.findById(VALID_QUEST_ID)).thenReturn(Optional.of(quest));
        when(gameEngine.startGame(user, quest)).thenReturn(initialState);
        when(gameRepository.save(any(Game.class))).thenReturn(expectedGame);

        Game result = gameService.startNewGame(VALID_QUEST_ID, VALID_USER_ID);

        assertEquals(expectedGame, result);
        verify(userService).findById(VALID_USER_ID);
        verify(questService).findById(VALID_QUEST_ID);
        verify(gameEngine).startGame(user, quest);
    }

    @Test
    @DisplayName("when startNewGame() then throw AppException if user not found")
    void whenStartNewGame_ThenUserNotFound() {
        when(userService.findById(NON_EXISTENT_USER_ID))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class,
                () -> gameService.startNewGame(VALID_QUEST_ID, NON_EXISTENT_USER_ID));

        assertEquals(Value.USER_NOT_FOUND, exception.getMessage());
        verify(userService).findById(NON_EXISTENT_USER_ID);
        verify(questService, never()).findById(anyLong());
        verify(gameEngine, never()).startGame(any(), any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("when startNewGame() then throw AppException if quest not found")
    void whenStartNewGame_ThenQuestNotFound() {
        when(userService.findById(VALID_USER_ID)).thenReturn(Optional.of(user));
        when(questService.findById(NON_EXISTENT_QUEST_ID))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class,
                () -> gameService.startNewGame(NON_EXISTENT_QUEST_ID, VALID_USER_ID));

        assertEquals(Value.QUEST_NOT_FOUND, exception.getMessage());
        verify(userService).findById(VALID_USER_ID);
        verify(questService).findById(NON_EXISTENT_QUEST_ID);
        verify(gameEngine, never()).startGame(any(), any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("advanceGame() should update game state when game exists")
    void testAdvanceGame_Success() {
        Game existingGame = createSavedGame();
        GameState nextState = createNextGameState();

        when(gameRepository.findById(VALID_GAME_ID))
                .thenReturn(Optional.of(existingGame));
        when(gameEngine.advanceGame(existingGame.getGameState(), VALID_ANSWER_ID))
                .thenReturn(nextState);
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
            Game updatedGame = invocation.getArgument(0);
            updatedGame.setId(VALID_GAME_ID);
            return updatedGame;
        });

        Game result = gameService.advanceGame(VALID_GAME_ID, VALID_ANSWER_ID);

        assertNotNull(result);
        assertEquals(VALID_GAME_ID, result.getId());
        assertEquals(nextState.getCurrentQuestion().getGeneratedId(), result.getCurrentQuestionId());
        assertEquals(nextState, result.getGameState());

        verify(gameEngine).advanceGame(
                argThat(state -> state.getCurrentQuestion().getGeneratedId().equals(1L)),
                eq(TestData.VALID_ANSWER_ID)
        );
        verify(gameRepository).save(result);
    }

    @Test
    @DisplayName("when getGameById() then return game if exists")
    void testGetGameById_Found() {
        Game expectedGame = createSavedGame();
        when(gameRepository.findById(VALID_GAME_ID))
                .thenReturn(Optional.of(expectedGame));

        Optional<Game> result = gameService.getGameById(VALID_GAME_ID);

        assertTrue(result.isPresent());
        assertEquals(expectedGame, result.get());
        verify(gameRepository).findById(VALID_GAME_ID);
    }

    @Test
    @DisplayName("when getGameById() then return empty Optional if game not found")
    void whenGetGameById_ThenNotFound() {
        when(gameRepository.findById(NON_EXISTENT_GAME_ID))
                .thenReturn(Optional.empty());

        Optional<Game> result = gameService.getGameById(NON_EXISTENT_GAME_ID);

        assertFalse(result.isPresent());
        verify(gameRepository).findById(NON_EXISTENT_GAME_ID);
    }


}
