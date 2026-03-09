package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.GameService;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class PlayGame implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGame.class.getName());

    private final GameService gameService;

    public PlayGame(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        LOGGER.info("Received GET request to play game");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        if (user == null) {
            LOGGER.error("User not authenticated. Throwing AppException: {}", AUTH_ERROR);
            throw new AppException(AUTH_ERROR);
        }
        LOGGER.debug("Authenticated user: id={}, login={}", user.getId(), user.getLogin());

        LOGGER.debug("Raw questId parameter: '{}'", req.getParameter(QUEST_ID));

        Long questId = Helpers.parseStringToLong(req.getParameter(QUEST_ID));
        Long gameId = req.getParameter(GAME_ID) != null
                ? Helpers.parseStringToLong(req.getParameter(GAME_ID))
                : null;

        LOGGER.debug("Quest ID: {}, Game ID: {}", questId, gameId);

        Game game;
        if (gameId == null) {
            LOGGER.info("Starting new game for quest ID: {}", questId);
            game = gameService.startNewGame(questId, user.getId());
        } else {
            LOGGER.info("Retrieving existing game by ID: {}", gameId);
            game = gameService.getGameById(gameId).orElseThrow(() -> new AppException(GAME_NOT_FOUND));
        }

        req.setAttribute(GAME, game);
        req.setAttribute(STATE, game.getGameState().isCompleted());
        req.setAttribute(QUEST, game.getGameState().getCurrentQuest());
        req.setAttribute(WINNING, game.getGameState().getCurrentQuestion().getLabel().contains(WIN));

        LOGGER.debug("Game attributes set in request: gameId={}, completed={}, questId={}",
                game.getId(), game.getGameState().isCompleted(), game.getGameState().getCurrentQuest().getId());

        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) throws AppException {
        LOGGER.info("Received POST request to advance game");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        Long gameId = Helpers.parseStringToLong(req.getParameter(GAME_ID));
        Long answerId = Helpers.parseStringToLong(req.getParameter(SELECTED_ANSWER_ID));

        LOGGER.debug("Advancing game ID: {} with answer ID: {}", gameId, answerId);
        Game updatedGame = gameService.advanceGame(gameId, answerId);

        req.setAttribute(GAME, updatedGame);
        req.setAttribute(USER, user);

        String redirectUrl = getView() + "?" + GAME_ID + "=" + updatedGame.getId() +
                "&" + QUEST_ID + "=" + updatedGame.getGameState().getCurrentQuest().getId();
        LOGGER.info("Game advanced successfully. Redirecting to: {}", redirectUrl);

        return redirectUrl;
    }
}
