package com.javarush.khmelov.repository;

import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Game;

public class GameRepository extends BaseRepository<Game> {


    public GameRepository(SessionCreator sessionCreator) {
        super(sessionCreator, Game.class);
    }
}
