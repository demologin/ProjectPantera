package com.javarush.goncharov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Game {
    private Long id;
    private Long questId;
    private String questName;
    private Long userId;
    private String userName;
    private Long currentQuestionId;
    private GameState gameState;
}
