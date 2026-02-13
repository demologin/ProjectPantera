package com.javarush.trukhanova.service;

import com.javarush.trukhanova.entity.QuestStep;


public interface GameLogic {
    QuestStep getNextStep(int id);
    boolean isGameOver(QuestStep step);
}