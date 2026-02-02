package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Game;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.game.GameEngine;
import com.javarush.vasileva.entity.GameState;
import com.javarush.vasileva.repository.InMemoryGameRepository;

import java.util.Optional;

import static com.javarush.vasileva.util.Value.*;

public class GameService {
    private final InMemoryGameRepository gameRepository;
    private final UserService userService;
    private final QuestService questService;
    private final GameEngine gameEngine;

    public GameService(InMemoryGameRepository gameRepository,
                       UserService userService,
                       QuestService questService,
                       GameEngine gameEngine) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.questService = questService;
        this.gameEngine = gameEngine;
    }

    public Game startNewGame(Long questId, Long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new AppException(USER_NOT_FOUND));
        Quest quest = questService.findById(questId).orElseThrow(() -> new AppException(QUEST_NOT_FOUND));
        GameState initialState = gameEngine.startGame(user, quest);
        Game game = Game.builder()
                .questId(questId)
                .userId(userId)
                .currentQuestionId(initialState.getCurrentQuestion().getGeneratedId())
                .gameState(initialState)
                .build();
        return gameRepository.save(game);
    }

    public Game advanceGame(Long gameId, Long answerId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new AppException("Game not found"));
        GameState nextState = gameEngine.advanceGame(game.getGameState(), answerId);
        game.setCurrentQuestionId(nextState.getCurrentQuestion().getGeneratedId());
        game.setGameState(nextState);
        return gameRepository.save(game);
    }

    public Optional<Game> getGameById(Long gameId) {
        return gameRepository.findById(gameId);
    }
}
