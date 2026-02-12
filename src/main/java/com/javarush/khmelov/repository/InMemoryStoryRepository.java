package com.javarush.khmelov.repository;
import com.javarush.khmelov.entity.Story;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStoryRepository implements StoryRepository {

    private final Map<String, Story> storage = new ConcurrentHashMap<>();

    public void put(Story story) {
        storage.put(story.getCode(), story);
    }

    @Override
    public Optional<Story> findByCode(String code) {
        return Optional.ofNullable(storage.get(code));
    }
}
