package com.javarush.trukhanova.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestStep {
    private int id;
    private String title;
    private String description;
    private String imagePath;
    private List<Answer> answers;
}