package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Quest;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor
public class QuestRepository implements Repository<Quest> {

    private final Map<Long, Quest> quests = new ConcurrentHashMap<>();
    private final AtomicLong generatedId = new AtomicLong(10);

    @Override
    public List<Quest> getAll() {
        return new ArrayList<>(quests.values());
    }

    @Override
    public Optional<Quest> get(long id) {
        return Optional.ofNullable(quests.get(id));
    }

    public void create(Quest quest) {
        quest.setId(generatedId.incrementAndGet());
        quests.put(quest.getId(), quest);
    }

    @Override
    public void update(Quest quest) {
        long questId = quest.getId();
        quests.put(questId, quest);
    }

    @Override
    public void delete(Quest entity) {

    }
}
