package com.javarush.goncharov.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Game {
    Long id;
    Long questId;
    String questName;
    Long userId;
    String userName;
    Long currentQuestionId;
    GameState gameState;
}
