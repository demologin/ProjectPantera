package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.GameService;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class PlayGame implements Command {

    private final GameService gameService;

    public PlayGame(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        if (user == null) {
            throw new AppException(AUTH_ERROR);
        }

        Long questId = Helpers.parseStringToLong(req.getParameter(QUEST_ID));
        Long gameId = req.getParameter(GAME_ID) != null
                ? Helpers.parseStringToLong(req.getParameter(GAME_ID))
                : null;

        Game game;
        if (gameId == null) {
            game = gameService.startNewGame(questId, user.getId());
        } else {
            game = gameService.getGameById(gameId).orElseThrow(() -> new AppException(GAME_NOT_FOUND));
        }

        req.setAttribute(GAME, game);
        req.setAttribute(STATE, game.getGameState().isCompleted());
        req.setAttribute(QUEST, game.getGameState().getCurrentQuest());
        req.setAttribute(WINNING, game.getGameState().getCurrentQuestion().getLabel().contains(WIN));

        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) throws AppException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        Long gameId = Helpers.parseStringToLong(req.getParameter(GAME_ID));
        Long answerId = Helpers.parseStringToLong(req.getParameter(SELECTED_ANSWER_ID));

        Game updatedGame = gameService.advanceGame(gameId, answerId);

        req.setAttribute(GAME, updatedGame);
        req.setAttribute(USER, user);

        return getView() + "?" + GAME_ID + "=" + updatedGame.getId() + "&" + QUEST_ID + "=" + updatedGame.getGameState().getCurrentQuest().getId();
    }
}
