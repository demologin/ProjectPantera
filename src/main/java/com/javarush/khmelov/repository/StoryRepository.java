package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Story;

import java.util.Optional;

public interface StoryRepository {
    Optional<Story> findByCode(String code);
}
