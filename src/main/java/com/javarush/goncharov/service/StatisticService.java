package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Game;
import com.javarush.goncharov.model.GameState;
import com.javarush.goncharov.model.Statistic;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.GameRepository;
import com.javarush.goncharov.repository.UserRepository;

import java.util.Collection;
import java.util.List;

public class StatisticService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public StatisticService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Statistic getUserStat(User user) {
        List<Game> games = gameRepository.findByUserId(user.getId()).toList();
        long win = games.stream().filter(game -> game.getGameState().equals(GameState.WIN)).count();
        long lose = games.stream().filter(game -> game.getGameState().equals(GameState.LOSE)).count();
        long play = games.stream().filter(game -> game.getGameState().equals(GameState.PLAY)).count();
        return Statistic.builder()
                .login(user.getLogin())
                .win(win)
                .lost(lose)
                .play(play)
                .total(win + lose + play)
                .build();
    }

    public Collection<Statistic> getAllUserStat() {
        return userRepository.getAll()
                .values()
                .stream()
                .map(this::getUserStat)
                .toList();
    }
}
