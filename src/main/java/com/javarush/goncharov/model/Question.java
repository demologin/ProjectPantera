package com.javarush.goncharov.model;

import java.util.ArrayList;
import java.util.Collection;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    Long id;
    Long questId;
    String questName;
    String text;
    GameState gameState;
    final Collection<Answer> answers = new ArrayList<>();
}
