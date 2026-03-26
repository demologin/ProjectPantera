package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Game;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryGameRepository implements GameRepository {

    private final Map<Long, Game> map = new ConcurrentHashMap<>();
    private final AtomicLong generatedId = new AtomicLong(0);

    @Override
    public Game save(Game game) {
        if (game.getId() == null) {
            game.setId(generatedId.incrementAndGet());
        }
        map.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void delete(Game game) {
        if (game != null && game.getId() != null) {
            map.remove(game.getId());
        }
    }
}
