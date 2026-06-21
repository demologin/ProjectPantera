package com.javarush.aleinik.repository.impl;

import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.repository.QuestRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.atomic.AtomicLong;

public class InMemoryQuestRepository implements QuestRepository {

    private final Map<Long, Quest> quests;
    private static final AtomicLong questIdSequence = new AtomicLong(1L);

    public InMemoryQuestRepository(){
        quests = new ConcurrentHashMap<>();
    }


    @Override
    public List<Quest> findAll() {
        return quests.values().stream().toList();
    }

    @Override
    public Quest findById(Long aLong) {
        return Optional.ofNullable(quests.get(aLong)).get();
    }

    @Override
    public Quest save(Quest entity) {
        Optional<Quest> existingQuest = findByTitle(entity.getTitle());

        if (existingQuest.isPresent()) {
            return existingQuest.get();
        }

        Long nextId = questIdSequence.getAndIncrement();
        entity.setId(nextId);
        quests.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public void deleteById(Long aLong) {
        quests.remove(aLong);
    }

    public Optional<Quest> findByTitle(String title){
        return quests.values().stream()
                .filter(q -> q.getTitle().equals(title))
                .findFirst();
    }

}
