package com.javarush.bekk.repository;

import com.javarush.bekk.entity.Game;

import java.util.stream.Stream;

public class GameRepository extends BaseRepository<Game> {
    @Override
    public Stream<Game> find(Game pattern) {
        return map.values()
                .stream()
                .filter(game -> nullOrEquals(pattern.getId(), game.getId()))
                .filter(game -> nullOrEquals(pattern.getQuestId(), game.getQuestId()))
                .filter(game -> nullOrEquals(pattern.getUserId(), game.getUserId()))
                .filter(game -> nullOrEquals(pattern.getCurrentQuestionId(), game.getCurrentQuestionId()))
                .filter(game -> nullOrEquals(pattern.getGameState(), game.getGameState()));
    }
}
