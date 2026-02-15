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
    public Optional<Quest> create(Quest quest) {
        quest.setId(id.incrementAndGet());
        update(quest);
        return Optional.of(quest);
    }

    @Override
    public Boolean delete(Quest quest) {
        int sizeBeforeDelete = map.size();
        map.remove(quest.getId());
        int sizeAfterDelete = map.size();
        return sizeBeforeDelete > sizeAfterDelete ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Optional<Quest> update(Quest quest) {
        map.put(quest.getId(), quest);
        return Optional.of(quest);
    }
}
