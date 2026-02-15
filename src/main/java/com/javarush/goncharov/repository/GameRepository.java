package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Game;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class GameRepository implements Repository<Game>{
    private final Map<Long, Game> map;
    public static final AtomicLong id = new AtomicLong();

    public GameRepository(Storage gameStorage) {
        this.map = gameStorage.getGames();
    }

    @Override
    public Optional<Game> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Game> findBy(String questName, String userName) {
        return map.values()
                .stream()
                .filter(u -> u.getQuestName().equals(questName))
                .filter(u -> u.getUserName().equals(userName))
                .findAny();
    }

    @Override
    public Map<Long, Game> getAll() {
        return map;
    }

    public Stream<Game> findByQuestId(Long pattern) {
        return map.values()
                .stream()
                .filter(u -> u.getQuestId().equals(pattern));
    }

    public Stream<Game> findByUserId(Long pattern) {
        return map.values()
                .stream()
                .filter(u -> u.getUserId().equals(pattern));
    }

    @Override
    public Optional<Game> create(Game game) {
        game.setId(id.incrementAndGet());
        update(game);
        return Optional.of(game);
    }

    @Override
    public Boolean delete(Game game) {
        int sizeBeforeDelete = map.size();
        map.remove(game.getId());
        int sizeAfterDelete = map.size();
        return sizeBeforeDelete > sizeAfterDelete ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Optional<Game> update(Game game) {
        map.put(game.getId(), game);
        return Optional.of(game);
    }
}
