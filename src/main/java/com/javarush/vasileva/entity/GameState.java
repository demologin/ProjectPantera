package com.javarush.vasileva.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState {
    private Quest currentQuest;
    private Question currentQuestion;
    private User user;
    private boolean isCompleted;
}
