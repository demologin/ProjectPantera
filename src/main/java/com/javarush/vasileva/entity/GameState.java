package com.javarush.vasileva.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameState {
    private Quest currentQuest;
    private Question currentQuestion;
    private User user;
    private boolean isCompleted;
}
