package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Quest;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class QuestRepository implements Repository<Quest>{
    private final Map<Long, Quest> map;
    public static final AtomicLong id = new AtomicLong();

    public QuestRepository(Storage questStorage) {
        this.map = questStorage.getQuests();
    }

    @Override
    public Optional<Quest> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Quest> findBy(String name, String authorName) {
        return map.values()
                .stream()
                .filter(u -> u.getName().equals(name))
                .filter(u -> u.getAuthorName().equals(authorName))
                .findAny();
    }

    @Override
    public Map<Long, Quest> getAll() {
        return map;
    }

    @Override
    public void create(Quest message) {
        message.setId(id.incrementAndGet());
        update(message);
    }

    @Override
    public void delete(Quest message) {
        map.remove(message.getId());
    }

    @Override
    public void update(Quest message) {
        map.put(message.getId(), message);
    }
}
