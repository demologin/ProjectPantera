package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Game;

import java.util.Optional;

public interface GameRepository {

    Game save(Game game);

    Optional<Game> findById(long id);

    void delete(Game game);
}
