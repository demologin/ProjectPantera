package com.javarush.vasileva.game;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.entity.User;
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
