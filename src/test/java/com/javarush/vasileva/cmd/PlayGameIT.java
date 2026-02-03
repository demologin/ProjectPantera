package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlayGameIT extends BaseIT {

    private final GameService gameService = mock(GameService.class);

    private final PlayGame playGame = new PlayGame(gameService);

    @Test
    @DisplayName("When GET request with new game then start new game and set attributes")
    void whenGetRequestWithNewGame_ThenStartNewGameAndSetAttributes() {
        Long questId = testQuest.getId();

        when(req.getParameter(QUEST_ID)).thenReturn(String.valueOf(questId));
        when(req.getParameter(GAME_ID)).thenReturn(null);

        when(session.getAttribute(USER)).thenReturn(testUser);
        when(gameService.startNewGame(questId, testUser.getId())).thenReturn(testGame);

        String view = playGame.doGet(req);

        assertEquals(playGame.getView(), view);

        verify(req).setAttribute(GAME, testGame);
        verify(req).setAttribute(STATE, testGame.getGameState().isCompleted());
        verify(req).setAttribute(QUEST, testGame.getGameState().getCurrentQuest());
        verify(req).setAttribute(WINNING,
                testGame.getGameState().getCurrentQuestion().getLabel().contains(WIN));

        verify(gameService).startNewGame(questId, testUser.getId());
    }

    @Test
    @DisplayName("When GET request with existing game then retrieve game and set attributes")
    void whenGetRequestWithExistingGame_ThenRetrieveGameAndSetAttributes() {
        when(req.getParameter(QUEST_ID)).thenReturn(String.valueOf(testQuest.getId()));
        when(req.getParameter(GAME_ID)).thenReturn(String.valueOf(testGame.getId()));

        when(session.getAttribute(USER)).thenReturn(testUser);
        when(gameService.getGameById(testGame.getId())).thenReturn(Optional.of(testGame));

        String view = playGame.doGet(req);

        assertEquals(playGame.getView(), view);
        verify(req).setAttribute(GAME, testGame);
        verify(req).setAttribute(STATE, testGame.getGameState().isCompleted());
        verify(req).setAttribute(QUEST, testGame.getGameState().getCurrentQuest());
        verify(req).setAttribute(WINNING,
                testGame.getGameState().getCurrentQuestion().getLabel().contains(WIN));
        verify(gameService).getGameById(testGame.getId());
    }

    @Test
    @DisplayName("When GET request but user not authenticated then throw AppException")
    void whenGetRequestButUserNotAuthenticated_ThenThrowAppException() {
        when(req.getParameter(QUEST_ID)).thenReturn(String.valueOf(testQuest.getId()));
        when(req.getParameter(GAME_ID)).thenReturn(null);

        when(session.getAttribute(USER)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> playGame.doGet(req));

        assertEquals(AUTH_ERROR, exception.getMessage());
        verify(gameService, never()).startNewGame(anyLong(), anyLong());
        verify(gameService, never()).getGameById(anyLong());
    }

    @Test
    @DisplayName("When GET request but game not found then throw AppException")
    void whenGetRequestButGameNotFound_ThenThrowAppException() {
        Long gameId = 999L;
        when(req.getParameter(QUEST_ID)).thenReturn(String.valueOf(testQuest.getId()));
        when(req.getParameter(GAME_ID)).thenReturn(String.valueOf(gameId));

        when(session.getAttribute(USER)).thenReturn(testUser);
        when(gameService.getGameById(gameId)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> playGame.doGet(req));

        assertEquals(GAME_NOT_FOUND, exception.getMessage());
        verify(gameService).getGameById(gameId);
    }

    @Test
    @DisplayName("When POST request then advance game and return redirect URL")
    void whenPostRequest_ThenAdvanceGameAndReturnRedirectUrl() throws AppException {
        when(req.getParameter(GAME_ID)).thenReturn(String.valueOf(testGame.getId()));
        when(req.getParameter(SELECTED_ANSWER_ID)).thenReturn(String.valueOf(testAnswer1.getId()));

        when(session.getAttribute(USER)).thenReturn(testUser);
        when(gameService.advanceGame(testGame.getId(), testAnswer1.getId())).thenReturn(testGame);

        String redirectUrl = playGame.doPost(req);
        String expectedUrl = playGame.getView() + "?" + GAME_ID + "=" + testGame.getId() +
                "&" + QUEST_ID + "=" + testGame.getGameState().getCurrentQuest().getId();

        assertEquals(expectedUrl, redirectUrl);
        verify(req).setAttribute(GAME, testGame);
        verify(req).setAttribute(USER, testUser);
        verify(gameService).advanceGame(testGame.getId(), testAnswer1.getId());
    }
}
