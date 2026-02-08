package com.javarush.trukhanova.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class QuestStep {
    private final int id;
    private final String title;
    private final String description;
    private final String imagePath;
    private final List<Answer> answers;
}