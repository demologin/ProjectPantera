package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Quest;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class QuestRepository implements Repository<Quest> {

    private final Map<Long, Quest> quests = new ConcurrentHashMap<>();

    @Override
    public List<Quest> getAll() {
        return new ArrayList<>(quests.values());
    }

    @Override
    public Optional<Quest> get(long id) {
        return Optional.ofNullable(quests.get(id));
    }

    @Override
    public void create(Quest entity) {
        quests.put(entity.getId(), entity);
    }

    @Override
    public void update(Quest entity) {

    }

    @Override
    public void delete(Quest entity) {

    }
}
